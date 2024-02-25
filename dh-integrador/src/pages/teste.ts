import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../components/Header';
import Footer from '../components/Footer';

const CadastroProduto = () => {
  // ... Outros estados
  const [caracteristicas, setCaracteristicas] = useState([]);
  const [caracteristicasSelecionadas, setCaracteristicasSelecionadas] = useState([]);

  useEffect(() => {
    const fetchCaracteristicas = async () => {
      try {
        const response = await axios.get('http://localhost:8080/caracteristica');
        setCaracteristicas(response.data);
      } catch (error) {
        console.error(error);
        alert('Ocorreu um erro ao carregar as características. Por favor, tente novamente.');
      }
    };
    fetchCaracteristicas();
  }, []);

  const handleCaracteristicaChange = (e) => {
    const idCaracteristica = parseInt(e.target.value);
    if (e.target.checked) {
      setCaracteristicasSelecionadas([...caracteristicasSelecionadas, idCaracteristica]);
    } else {
      setCaracteristicasSelecionadas(caracteristicasSelecionadas.filter(id => id !== idCaracteristica));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    const token = localStorage.getItem('token');
  
    const data = {
      nomeProduto,
      descricaoProduto,
      cidade: {
        idCidade,
      },
      caracteristicas: caracteristicasSelecionadas.map(id => ({ id })),
    };
  
    // ...restante do código do handleSubmit
  };

  // ...restante do código do componente

  return (
    <>
      <Header />
      <div className="container">
        <div className="row">
          <div className="col-12">
            <h1 className="text-center">Cadastro de Produto</h1>
            <form onSubmit={handleSubmit}>
              {/* ... Outros campos do formulário */}
              <div className="form-group">
                <label>Características:</label>
                <div className="row">
                  {caracteristicas.map((caracteristica) => (
                    <div key={caracteristica.idCaracteristica} className="col-md-4">
                      <div className="form-check">
                        <input
                          type="checkbox"
                          className="form-check-input"
                          id={`caracteristica-${caracteristica.idCaracteristica}`}
                          value={caracteristica.idCaracteristica}
                          onChange={handleCaracteristicaChange}
                        />
                        <label className="form-check-label" htmlFor={`caracteristica-${caracteristica.idCaracteristica}`}>
                          <img src={caracteristica.iconeCaracteristica} alt={caracteristica.nomeCaracteristica} className="mr-2" />
                          {caracteristica.nomeCaracteristica}
                        </label>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
              <button type="submit" className="btn btn-primary 
              mt-3 bg-color1">
Cadastrar Produto
</button>
</form>
</div>
</div>
</div>
<Footer />
</>
);
};

export default CadastroProduto;
