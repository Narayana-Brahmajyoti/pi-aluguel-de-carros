// import React, { useState, useEffect } from 'react';
// import axios from 'axios';
// import Header from '../components/Header';
// import Footer from '../components/Footer';
// import DatePicker from "react-datepicker";
// import "react-datepicker/dist/react-datepicker.css";
// import { sdk } from '@mercadopago/sdk';

// const Reserva = ({ match }) => {
//   const [usuario, setUsuario] = useState({});
//   const [produto, setProduto] = useState({});
//   const [dataInicioReserva, setDataInicioReserva] = useState(new Date());
//   const [dataFinalReserva, setDataFinalReserva] = useState(new Date());
//   const idProduto = match.params.idProduto;
//   const userId = localStorage.getItem("id");
//   console.log("userId:", userId);

//   useEffect(() => {
//     const fetchUsuario = async () => {
//       const token = localStorage.getItem('token');
//       const response = await axios.get(`http://localhost:8080/usuario/${userId}`, {
//         headers: { Authorization: `Bearer ${token}` },
//       });
//       setUsuario(response.data);
//       console.log("Dados do usuário:", response.data);
//     };

//     const fetchProduto = async () => {
//       const response = await axios.get(`http://localhost:8080/produto/${idProduto}`);
//       setProduto(response.data);
//       console.log("Dados do produto:", response.data);
//     };

//     fetchUsuario();
//     fetchProduto();
//   }, [userId, idProduto]);

//   const handleSubmit = async (e) => {
//     // ...
//   };

//   const handleMercadoPago = () => {
//     // Aqui você deve adicionar sua chave de acesso do MercadoPago
//     sdk.configure({ access_token: 'TEST-1834645059415936-092611-897767f8dd80df32cdc5527fc70b19b7__LC_LD__-230760483' });

//     const preference = {
//       items: [
//         {
//           title: produto.nomeProduto,
//           unit_price: 600,
//           quantity: 1,
//         },
//       ],
//     };

//     sdk.preferences.create(preference).then((response) => {
//       window.open(response.body.init_point, '_blank');
//     }).catch((error) => {
//       console.error("Erro ao criar preferência do MercadoPago:", error);
//       alert('Ocorreu um erro ao criar a preferência do MercadoPago. Por favor, tente novamente.');
//     });
//   };

//   return (
//     // ...
//               <button type="submit" className="btn btn-primary mt-3 bg-color1">
//                 Confirmar Reserva
//               </button>
//               <button onClick={handleMercadoPago} className="btn btn-primary mt-3 bg-color1 ml-3">
//                 Pagar com MercadoPago
//               </button>
//             </form>
//     // ...
//   );
// };

// export default Reserva;
