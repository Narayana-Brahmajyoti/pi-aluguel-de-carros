import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { useParams, useHistory } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShareNodes, faHeart, faChevronLeft } from '@fortawesome/free-solid-svg-icons';
import { DateRangePicker } from 'react-date-range';
import 'react-dates/initialize';
import 'react-dates/lib/css/_datepicker.css';
import { pt } from 'date-fns/locale';


const DetalhesProduto = () => {
  const { idProduto } = useParams();
  const history = useHistory();
  const [produto, setProduto] = useState(null);
  const placeholderImage = 'https://via.placeholder.com/150';
  localStorage.setItem("productId", idProduto);
  const userId = localStorage.getItem("userId");
  console.log("userId:", userId); // Adicione esta linha para verificar o valor de userId

  


  


  useEffect(() => {
    const fetchProduto = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/produto/${idProduto}`);
        setProduto(response.data);
      } catch (error) {
        console.error('Erro ao buscar produto:', error);
      }
    };

    fetchProduto();
  }, [idProduto]);
  
  const handleIniciarReserva = () => {
    localStorage.setItem("productId", idProduto);
    
    history.push(`/reserva/${idProduto}`); }
    // Verificar se o usuário está logado e redirecionar para a página de reserva
    /* if (verificarUsuarioLogado()) { */
      
    /* } else {
      history.push(`/login?redirect=reserva/${idProduto}`);
    } */
  /* }; */
  

  
  const handleDateRangeChange = (ranges) => {
    setSelectedDate(ranges.selection);
  };
  const [isOpen, setIsOpen] = useState(true);
  const [selectedDate, setSelectedDate] = useState({
    startDate: null,
    endDate: null,
    key: 'selection',
  });


  // Função para voltar à tela anterior
  const voltarTelaAnterior = () => {
    history.goBack();
  };
  return (
    <>
      <Header />
      <div className="main-content">
        <div className="">
          {/* Bloco do Título e Categoria */}
          {produto ? (
            <>
              <div className="row bg-color2 text-white py-3 px-5 d-flex align-items-center">
                <div className="col-md-11">
                  <h2>{produto.nomeProduto}</h2>
                  <h6>Categoria: {produto.categoria && produto.categoria.categoria}</h6>
                </div>
                <div className="col-md-1 text-end">
          <FontAwesomeIcon
            icon={faChevronLeft}
            size="2xl"
            style={{ color: "#ffffff" }}
            onClick={voltarTelaAnterior}
          />
        </div>
              </div>


              {/* Bloco de Localização */}
              <div className="row bg-color3 py-3 px-5 align-items-center">
                <div className="col-md-11">
                  <h6>Cidade: {produto.cidade && produto.cidade.nomeCidade}</h6>
                </div>
                <div className='col-md-1'>


                </div>
              </div>


              {/* Bloco de Imagens */}
              <div className='container'>
                <div className="row d-flex align-items-center">
                  <div className='social-share-icon  col-md-12 my-3 '>
                    <FontAwesomeIcon icon={faShareNodes} size="2xl" /> &nbsp;&nbsp;&nbsp; <FontAwesomeIcon icon={faHeart} size="2xl" />
                  </div>
                </div>
                <div className='row'>

                  <div className="col-md-6">
                    <img
                      src={produto.imagem && produto.imagem[0] ? produto.imagem[0].urlImagem : placeholderImage}
                      alt={produto.imagem && produto.imagem[0] ? produto.imagem[0].tituloImagem : 'Imagem não disponível'}
                      className="img-fluid h-100"
                    />
                  </div>
                  <div className="images-detalhes col-md-6">
                    <div className='row h-50'>
                      {produto.imagem.slice(1).map((img, index) => (
                        <img
                          key={index}
                          src={img.urlImagem}
                          alt={img.tituloImagem}
                          className="col-md-6 my-1"
                        />
                      ))}
                      {/* <div className='vermais-produto text-end'> ver mais</div> */}
                    </div>
                  </div>
                </div>

              </div>

              {/* Bloco de Descrição */}
              <div className="container py-3">
                <div className="row d-flex align-item-center">
                  <div className="col-md-12">
                    <h3>Descrição:</h3>
                    <p>{produto.descricaoProduto}</p>
                  </div>
                </div>
              </div>


              {/* Bloco de Características */}
              <div className="container py-3">
                <div className="row">
                  <h3 className='caracteristicas py-1'>Caracteristicas :</h3>
                  {produto.caracteristicas.map((caracteristica) => (
                    <div className="col-lg-3 col-md-6 col-sm-12" key={caracteristica.idCaracteristica}>
                      <div className="d-flex align-items-center mb-2">
                        <img src={caracteristica.iconeCaracteristica} alt={caracteristica.nomeCaracteristica} style={{ marginRight: '8px' }} />
                        {caracteristica.nomeCaracteristica}
                      </div>
                    </div>
                  ))}
                </div>

              </div>

              {/* Bloco de Política */}
              <div className="container py-3">
                <div className="row">
                  <h3 className='caracteristicas py-1'>Politicas:</h3>
                  <div className="col-lg-4 col-md-6 col-sm-12">
                    <div className="mb-4">
                      <h5>Normas</h5>
                      <p>As normas para aluguel de carros incluem a idade mínima do motorista, a necessidade de apresentar uma CNH válida e, em alguns casos, um cartão de crédito em nome do motorista. É importante verificar as normas específicas da locadora antes de realizar o aluguel.</p>
                    </div>
                  </div>
                  <div className="col-lg-4 col-md-6 col-sm-12">
                    <div className="mb-4">
                      <h5>Segurança</h5>
                      <p>Os carros disponíveis para aluguel passam por manutenção e inspeção regularmente para garantir a segurança dos clientes. Além disso, a maioria das locadoras oferece a opção de contratar seguros adicionais, como proteção contra roubo, danos e cobertura de terceiros, para proporcionar maior tranquilidade durante a locação.</p>
                    </div>
                  </div>
                  <div className="col-lg-4 col-md-6 col-sm-12">
                    <div className="mb-4">
                      <h5>Cancelamento</h5>
                      <p>A política de cancelamento varia de acordo com a locadora e o tipo de reserva. Em geral, é possível cancelar a reserva sem custos adicionais até um determinado período antes do início da locação. Para evitar surpresas, é fundamental verificar a política de cancelamento específica da locadora antes de confirmar a reserva.</p>
                    </div>
                  </div>
                </div>
              </div>


              {/* Bloco de Disponibilidade e Botão de Reserva */}
              <div className="container py-3">
                <div className="row">
                  <h3>Datas Disponiveis:</h3>
                  <div className="calendar col-lg-8 col-md-8 col-sm-12">
                    
                    {isOpen && (
                      <DateRangePicker
                        ranges={[selectedDate]}
                        onChange={handleDateRangeChange}
                        locale={pt}
                        months={2}
                        direction="horizontal"
                        showDateDisplay={true}
                        staticRanges={[]}
                        inputRanges={[]}
                        minDate={new Date()}
                        onClose={() => setIsOpen(true)}
                      />
                    )}
                  </div>
                  <div className="p-3 col-lg-4 col-md-4 col-sm-12 align-items-center text-center">
                              <div className='botao-reserva p-5'>
                              <h5>Adiciona as datas de sua reserva para obter os preços:</h5>
                              <button className="btn btn-primary bg-color1" onClick={handleIniciarReserva}>Iniciar Reserva</button>
                              </div>
                            </div>

                </div>
                
                            
              </div>



            </>
          ) : (
            <div>
              <h1>Carregando detalhes do produto...</h1>
            </div>
          )}
        </div>

      </div>
      <Footer />
    </>
  );
};

export default DetalhesProduto;