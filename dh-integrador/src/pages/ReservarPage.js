import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../components/Header';
import Footer from '../components/Footer';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const Reserva = ({ match }) => {
  const [usuario, setUsuario] = useState({});
  const [produto, setProduto] = useState({});
  const [dataInicioReserva, setDataInicioReserva] = useState(new Date());
  const [dataFinalReserva, setDataFinalReserva] = useState(new Date());
  const [horarioInicioReserva, setHorarioInicioReserva] = useState('00:00');
  const idProduto = match.params.idProduto;
  const userId = localStorage.getItem("userId");
  console.log("userId:", userId);

  useEffect(() => {
    
    const fetchUsuario = async () => {
      const token = localStorage.getItem('token');
      const response = await axios.get(`http://localhost:8080/usuario/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setUsuario(response.data);
      console.log("Dados do usuário:", response.data);
    };

    const fetchProduto = async () => {
      const response = await axios.get(`http://localhost:8080/produto/${idProduto}`);
      setProduto(response.data);
      console.log("Dados do produto:", response.data);
    };

    fetchUsuario();
    fetchProduto();
  }, [userId, idProduto]);

  const gerarHorarios = () => {
    const horarios = [];
  
    for (let i = 0; i < 24; i++) {
      for (let j = 0; j < 60; j += 15) {
        const hora = i < 10 ? `0${i}` : i;
        const minuto = j < 10 ? `0${j}` : j;
        horarios.push(`${hora}:${minuto}`);
      }
    }
  
    return horarios;
  };
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
  
    const dataInicioComHorario = new Date(dataInicioReserva);
    const [horaInicio, minutoInicio] = horarioInicioReserva.split(':');
    dataInicioComHorario.setHours(horaInicio);
    dataInicioComHorario.setMinutes(minutoInicio);
  
    const data = {
      horarioInicioReserva: dataInicioComHorario.toISOString().slice(11, 19),
      dataInicioReserva: dataInicioReserva.toISOString().slice(0, 10),
      dataFinalReserva: dataFinalReserva.toISOString().slice(0, 10),
      produto: {
        idProduto: parseInt(idProduto),
      },
      usuario: {
        id: parseInt(userId),
      },
    };
  
    console.log("Dados enviados:", data);
  
    try {
      const response = await axios.post('http://localhost:8080/reserva', data, {
        headers: { Authorization: `Bearer ${token}` },
      });
  
      if (response.status === 201) {
        alert('Reserva realizada com sucesso!');
      } else {
        alert('Ocorreu um erro ao realizar a reserva. Por favor, tente novamente.');
      }
    } catch (error) {
      console.error(error);
     
      alert('Ocorreu um erro ao realizar a reserva. Por favor, tente novamente.');
    }
    };
    
    return (
    <>
    <Header />
    <div className="container">
    <div className="row my-1">
    <div className='text-center my-5'>
    <h1> Sua reserva está quase pronta! </h1>
    </div>
    <div className="col-md-6">
    <h2>Dados do Usuário</h2>
    <p>Nome: {usuario.nome} {usuario.nomeUsuario}</p>
    <p>Email: {usuario.emailUsuario}</p>
    <h3>Selecione as datas</h3>
    <form onSubmit={handleSubmit}>
    <div className="form-group">
            <label htmlFor="dataInicioReserva">Data de início:</label>
            <DatePicker
              id="dataInicioReserva"
              className="form-control"
              selected={dataInicioReserva}
              onChange={(date) => setDataInicioReserva(date)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="dataFinalReserva">Data final:</label>
            <DatePicker
              id="dataFinalReserva"
              className="form-control"
              selected={dataFinalReserva}
              onChange={(date) => setDataFinalReserva(date)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="horarioInicioReserva">Horário de início:</label>
            <select
              id="horarioInicioReserva"
              className="form-control"
              value={horarioInicioReserva}
              onChange={(e) => setHorarioInicioReserva(e.target.value)}
            >
              {gerarHorarios().map((horario) => (
                <option key={horario} value={horario}>
                  {horario}
                </option>
              ))}
            </select>
          </div>

          <button type="submit" className="btn btn-primary mt-3 bg-color1">
            Confirmar Reserva
          </button>
        </form>
      </div>
      <div className="col-md-6">
        <h2>Produto Selecionado:</h2>
        <h3>{produto.nomeProduto}</h3>
        <img
          src={produto.imagem && produto.imagem[0] ? produto.imagem[0].urlImagem : 'https://via.placeholder.com/150'}
          alt={produto.nomeProduto}
          style={{ width: '100%' }}
        />
        <p>Cidade: {produto.cidade && produto.cidade.nomeCidade}</p>
        <p>Descrição: {produto.descricaoProduto}</p>
      </div>
    </div>
  </div>
  <Footer />
</>
);
};

export default Reserva;