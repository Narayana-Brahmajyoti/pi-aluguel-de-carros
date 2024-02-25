package com.DH.PI.service;

import com.DH.PI.controller.ProdutoController;
import com.DH.PI.controller.ReservaController;
import com.DH.PI.dtoIN.ReservaDTO;
import com.DH.PI.dtoOut.EmailDTO;
import com.DH.PI.dtoOut.ReservaDTOOUT;
import com.DH.PI.dtoOut.UsuarioDTOOUT;
import com.DH.PI.exception.ResourceBadRequest;
import com.DH.PI.exception.ResourceConflict;
import com.DH.PI.exception.ResourceNotFoundException;
import com.DH.PI.model.EmailModel;
import com.DH.PI.model.ProdutoModel;
import com.DH.PI.model.ReservaModel;
import com.DH.PI.model.UsuarioModel;
import com.DH.PI.repository.ProdutoRepository;
import com.DH.PI.repository.ReservaRepository;
import com.DH.PI.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.module.ResolutionException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ReservaService {
    @Autowired
    ReservaRepository repository;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    EmailService emailService;

    private static final Logger logger = LogManager.getLogger(UsuarioModel.class);

    public ReservaModel save(ReservaDTO reservaDTO) throws ResourceNotFoundException, ResourceConflict, ResourceBadRequest {
        Date dataAtual = new Date();
       if ( reservaDTO.getDataInicioReserva().before(dataAtual)){
           throw new ResourceBadRequest("A data de inicio da reserva, não pode ser antes da data atual");
       }
        usuarioRepository.findById(reservaDTO.getUsuario().getId()).orElseThrow(()-> new ResourceNotFoundException("Usuario não existe"));
        ProdutoModel produtoModel = produtoRepository.findById(reservaDTO.getProduto().getIdProduto())
                .orElseThrow(()-> new ResourceNotFoundException("Produto não existe"));
        ObjectMapper mapper = new ObjectMapper();
        Date dataInicial = reservaDTO.getDataInicioReserva();
        Date dataFinal = reservaDTO.getDataFinalReserva();
        boolean hasConflict = produtoModel.getReserva().stream()
                .anyMatch(r ->{
                    Date rDataInicial = r.getDataInicioReserva();
                    Date rDateFinal = r.getDataFinalReserva();
                    return (dataInicial.after(rDataInicial)&& dataInicial.before(rDateFinal))
                            || (dataFinal.after(rDataInicial)&& dataFinal.before(rDateFinal))
                            || dataInicial.equals(rDataInicial)
                            || dataFinal.equals(rDateFinal);
                });
        if (hasConflict){
            throw new ResourceConflict("Produto indisponivel para essa data");
        }
        ReservaModel reserva = mapper.convertValue(reservaDTO,ReservaModel.class);
        logger.info("Salvando produto");
        produtoModel.getReserva().add(reserva);
        produtoRepository.save(produtoModel);

        Long id = reservaDTO.getProduto().getIdProduto();
        emailReserva(reserva, id);
        return reserva;

    }
    public  List<ReservaDTOOUT> findAll() throws ResourceNotFoundException {
        List<ReservaModel> reservaModelList = repository.findAll();
        if (reservaModelList.isEmpty()){
            throw  new ResourceNotFoundException("Nenhuma reserva encontrada");
        }
        List<ReservaDTOOUT> reservaDTOOUTList = new ArrayList<>();
        for (ReservaModel r : reservaModelList){
            ObjectMapper mapper = new ObjectMapper();
            reservaDTOOUTList.add(mapper.convertValue(r, ReservaDTOOUT.class));
        }
        return reservaDTOOUTList;
    }

    public ReservaDTOOUT findById(Long id) throws ResourceNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        ReservaModel reservaModel = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Nenhuma reserva encontrada"));
        ReservaDTOOUT reservaDTOOUT = mapper.convertValue(reservaModel, ReservaDTOOUT.class);
        return  reservaDTOOUT;
    }
    public List<ReservaDTOOUT> findByIdUsuario(Long id) throws ResourceNotFoundException {
        List<ReservaModel> reservaModelList = repository.findByUsuarioId(id);
        if (reservaModelList.isEmpty()){
            throw  new ResourceNotFoundException("Nenhuma reserva encontrada");
        }
        List<ReservaDTOOUT> reservaDTOOUTList = new ArrayList<>();
        for (ReservaModel r : reservaModelList){
            ObjectMapper mapper = new ObjectMapper();
            reservaDTOOUTList.add(mapper.convertValue(r, ReservaDTOOUT.class));
        }
        return reservaDTOOUTList;
    }
    public ReservaDTOOUT change(ReservaModel reserva) throws ResourceNotFoundException, ResourceConflict {
        ObjectMapper mapper = new ObjectMapper();
        repository.findById(reserva.getIdReserva()).orElseThrow(()-> new ResourceNotFoundException("Reserva não encontrada"));
            //List<ReservaModel> reservaModelList = repository.findByConsultaReserva(reserva.getProduto().getIdProduto(),reserva.getDataInicioReserva() ,reserva.getDataFinalReserva());
        List<ReservaModel> reservaModelList = repository.findByConsultaReserva(reserva.getProduto().getIdProduto(),reserva.getDataInicioReserva() ,reserva.getDataFinalReserva());
        List<ReservaModel> reservaModelList1 = repository.findByConsultaReserva1(reserva.getProduto().getIdProduto(),reserva.getDataInicioReserva() ,reserva.getDataFinalReserva());
            if (!reservaModelList.isEmpty() || !reservaModelList1.isEmpty()){
                logger.error("Produto indisponivel nessa data");
                throw new ResourceConflict("Este produto está indisponivel para essa data");
            }
            repository.save(reserva);

            ReservaDTOOUT reservaDTOOUT = mapper.convertValue(reserva, ReservaDTOOUT.class);
            reservaDTOOUT.setProduto(reserva.getProduto());
            return reservaDTOOUT;
    }
    public void delete(Long id) throws ResourceNotFoundException {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));
        logger.info("Exclusão de reserva");
        repository.deleteById(id);
    }

    public void emailReserva(ReservaModel reservaModel, Long id) throws ResourceNotFoundException {
        UsuarioModel usuarioModel = usuarioRepository.findById(reservaModel.getUsuario().getId()).orElseThrow(()-> new ResourceNotFoundException("Usuario não encontrado"));
        ProdutoModel produtoModel = produtoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));
        EmailDTO emailDTO = new EmailDTO();

        emailDTO.setOwnerRef("VrumVrum");
        emailDTO.setEmailFrom("VrumVrumcars2023@gmail.com");
        emailDTO.setEmailTo(usuarioModel.getEmailUsuario());
        emailDTO.setSubject("Confirmação de Reserva");
        emailDTO.setText("Ola "+usuarioModel.getNomeUsuario()+" sua reserva  para o veiculo "
                + produtoModel.getNomeProduto()+ " no periodo de "
                +reservaModel.getDataInicioReserva()+ " a "+reservaModel.getDataFinalReserva()
                + " foi confirmada. Para mais detalhes consulte o site. ");
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDTO, emailModel);
        emailService.sendEmail(emailModel);



    }


}
