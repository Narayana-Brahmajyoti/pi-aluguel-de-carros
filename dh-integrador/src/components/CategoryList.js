
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

const CategoryList = () => {
  const [categorias, setCategorias] = useState([]);
  const history = useHistory();

  useEffect(() => {
    const fetchCategorias = async () => {
      try {
        const response = await axios.get('http://localhost:8080/categoria');
        setCategorias(response.data);
      } catch (error) {
        console.error('Erro ao buscar categorias:', error);
      }
    };

    fetchCategorias();
  }, []);

  const handleCategoryClick = (categoriaId) => {
    history.push(`/resultados-categoria/${categoriaId}`);
  };

  return (
    <div className="container">
      <h1>Categorias</h1>
      <div className="row">
        {categorias.map((categoria) => (
          <div key={categoria.id} className="col-md-4">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">{categoria.categoria}</h5>
                <button className="btn btn-primary" onClick={() => handleCategoryClick(categoria.id)}>
                  Ver produtos desta categoria
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CategoryList;
