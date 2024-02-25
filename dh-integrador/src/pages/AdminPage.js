import React, { useState, useEffect } from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import api from '../services/authService';

const AdminPage = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [reservas, setReservas] = useState([]);

  useEffect(() => {
    async function fetchUsuarios() {
      try {
        const response = await api.get('/usuario');
        setUsuarios(response.data);
      } catch (error) {
        console.error('Erro ao buscar os usuários:', error);
      }
    }

    async function fetchReservas() {
      try {
        const response = await api.get('/reserva');
        setReservas(response.data);
      } catch (error) {
        console.error('Erro ao buscar as reservas:', error);
      }
    }

    fetchUsuarios();
    fetchReservas();
  }, []);

  return (
    <div>
      <Header />
      <div className="container mt-4">
        <h1 className="text-center">Página do Administrador</h1>
        <p className="text-center">Bem-vindo à página do administrador. Aqui você pode gerenciar o conteúdo do site.</p>
      </div>
      <div className="container mt-5">
        <h2>Lista de Usuários</h2>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nome do usuário</th>
              <th>Email</th>
              <th>Permissão</th>
            </tr>
          </thead>
          <tbody>
            {usuarios.map((usuario) => (
              <tr key={usuario.id}>
                <td>{usuario.id}</td>
                <td>{usuario.nomeUsuario}</td>
                <td>{usuario.emailUsuario}</td>
                <td>{usuario.permisao && usuario.permisao[0] ? usuario.permisao[0].authority : 'Sem autoridade'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="container mt-5">
        <h2>Lista de Reservas</h2>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>Usuário</th>
              <th>Carro</th>
              <th>Data da reserva</th>
            </tr>
          </thead>
          <tbody>
            {reservas.map((reserva) => (
              <tr key={reserva.id}>
                <td>{reserva.id}</td>
                <td>{reserva.nomeUsuario}</td>
                {/* <td>{reserva.produto}</td>
                <td>{reserva.dataReserva}</td> */}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <Footer />
    </div>
  );
};

export default AdminPage;
