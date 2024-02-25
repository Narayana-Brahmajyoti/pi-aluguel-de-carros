import React from 'react';
import { Link } from 'react-router-dom';

const ProductCard = ({ produto }) => {
  const placeholderImage = 'https://via.placeholder.com/150';

  return (
    <div className="card mb-4">
      <div className="row g-0">
        <div className="col-md-4">
          <img
            src={produto.imagem && produto.imagem[0] ? produto.imagem[0].urlImagem : placeholderImage}
            alt={produto.imagem && produto.imagem[0] ? produto.imagem[0].tituloImagem : 'Imagem indisponível'}
            className="card-img-top h-100"
          />
        </div>
        <div className="col-md-8">
          <div className="card-body">
            <h5 className="card-title">{produto.nomeProduto} productcard</h5>
            <p className="card-text">{produto.descricaoProduto}</p>
            <p className="card-text">
              <strong>Categoria:</strong> {produto.categoria && produto.categoria.categoria}
            </p>
            <Link to={`/produto/${produto.idProduto}`} className="btn btn-primary">
              ver mais
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
