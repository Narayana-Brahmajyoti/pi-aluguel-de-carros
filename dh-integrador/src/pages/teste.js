import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../components/Header';
import Footer from '../components/Footer';

const CadastroProduto = () => {
  // (restante do código permanece o mesmo)

  return (
    <>
      <Header />
      <div className="container">
        {/* restante do código permanece o mesmo */}
        <div className="form-group">
          <label>Imagens:</label>
          {imagens.map((imagem, index) => (
            <div key={index}>
              <label htmlFor={`tituloImagem${index}`}>Título da imagem:</label>
              <input
                type="text"
                className="form-control"
                id={`tituloImagem${index}`}
                value={imagem.tituloImagem}
                onChange={(e) => {
                  const newImagens = [...imagens];
                  newImagens[index].tituloImagem = e.target.value;
                  setImagens(newImagens);
                }}
                required
              />
              <label htmlFor={`urlImagem${index}`}>URL da imagem:</label>
              <input
                type="text"
                className="form-control"
                id={`urlImagem${index}`}
                value={imagem.urlImagem}
                onChange={(e) => {
                  const newImagens = [...imagens];
                  newImagens[index].urlImagem = e.target.value;
                  setImagens(newImagens);
                }}
                required
              />
            </div>
          ))}
          <button type="button" className="btn btn-secondary mt-2" onClick={addImagem}>
            Adicionar imagem
          </button>
        </div>
        <button type="submit" className="btn btn-primary mt-3">
          Cadastrar Produto
        </button>
      </div>
      <Footer />
    </>
  );
};

export default CadastroProduto;

