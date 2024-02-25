import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, Link } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';

const Resultados = () => {
  const [produtos, setProdutos] = useState([]);
  const location = useLocation();
  const { cityName } = location.state;

  useEffect(() => {
    const fetchProdutosPorCidade = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/produto/filter?cidade=${cityName}`);
        setProdutos(response.data);
      } catch (error) {
        console.error('Erro ao buscar produtos por cidade:', error);
      }
    };

    fetchProdutosPorCidade();
  }, [cityName]);

  return (
    <div>
      <Header />
      <div className="main-content">
        <div className="container">
          <h1>Produtos em {cityName}</h1>
          <div className="row">
            {produtos.map((produto) => (
              <div key={produto.idProduto} className="col-md-4">
                <div className="card">
                  {produto.imagem && produto.imagem[0] && (
                    <img
                      src={produto.imagem[0].urlImagem}
                      alt={produto.imagem[0].tituloImagem}
                      className="card-img-top"
                    />
                  )}
                  <div className="card-body">
                    <h5 className="card-title">{produto.nomeProduto}</h5>
                    <p className="card-text">{produto.descricaoProduto}</p>
                    <Link to={`/produto/${produto.idProduto}`} className="btn btn-primary">
                      ver mais
                    </Link>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Resultados;
