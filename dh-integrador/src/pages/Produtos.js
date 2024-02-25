import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { useParams } from 'react-router-dom';

const Produto = () => {
  const [produto, setProduto] = useState(null);
  const { idProduto } = useParams();

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

  if (!produto) {
    return <div>Carregando...</div>;
  }

  return (
    <div>
      <Header />
      <div className="container">
      <div class="pt-1 pb-3 search-bar bg-color2">
      <h3>{produto.nomeProduto}</h3>
      </div>
        
        <div className="row">
          <div className="col-md-6">
            {produto.imagem.slice(0, 5).map((img, index) => (
              <img
                key={index}
                src={img.urlImagem}
                alt={img.tituloImagem}
                className="img-fluid mb-3"
                style={{ maxWidth: '100%' }}
              />
            ))}
          </div>
          <div className="col-md-6">
            <h5>Descrição</h5>
            <p>{produto.descricaoProduto}</p>
            <h5>Categoria</h5>
            <p>{produto.categoria && produto.categoria.categoria}</p>
            <h5>Cidade</h5>
            <p>{produto.cidade && produto.cidade.nomeCidade}, {produto.cidade && produto.cidade.paisCidade}</p>
            <h5>Características</h5>
            <ul>
              {produto.caracteristicas.map((caracteristica) => (
                <li key={caracteristica.idCaracteristica}>
                  {caracteristica.nomeCaracteristica}
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Produto;
