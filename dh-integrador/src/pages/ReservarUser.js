import React, { useState, useEffect } from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import api from '../services/authService';

const ReservarUser = () => {
  const [reservas, setReservas] = useState([]);

  useEffect(() => {
    async function buscarReservas() {
      try {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('userId');

        console.log('Token:', token);
        console.log('ID do usuário:', userId);

        const response = await api.get(`/reserva/findusuario?id=${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        console.log('Resposta da API:', response);

        setReservas(response.data);
      } catch (error) {
        console.error('Erro ao buscar as reservas:', error);
      }
    }

    buscarReservas();
  }, []);

  console.log('Reservas:', reservas);

  return (
    <div>
      <Header />
      <div className="container mt-4">
        <h1 className="text-center">Informações das reservas</h1>
      </div>
      <div className="container mt-5">
        <h2>Lista de Reservas</h2>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>ID da Reserva</th>
              <th>Horário de início</th>
              <th>Data de início</th>
              <th>Data final</th>
              <th>Nome do produto</th>
              <th>Cidade do produto</th>
            </tr>
          </thead>
          <tbody>
            {reservas.map((reserva) => (
              <tr key={reserva.idReserva}>
                <td>{reserva.idReserva}</td>
                <td>{reserva.horarioInicioReserva}</td>
                <td>{reserva.dataInicioReserva}</td>
                <td>{reserva.dataFinalReserva}</td>
                <td>{reserva.produto?.nomeProduto}</td>
                <td>{reserva.produto?.cidade?.nomeCidade}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <Footer />
    </div>
  );
};

export default ReservarUser;
