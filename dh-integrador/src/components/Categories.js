import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

const Categories = () => {
  const [categories, setCategories] = useState([]);
  const history = useHistory();

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get('http://localhost:8080/categoria');
        setCategories(response.data);
      } catch (error) {
        console.error('Erro ao buscar categorias:', error);
      }
    };

    fetchCategories();
  }, []);

  const handleCategoryClick = (categoriaNome) => {
    history.push('/category-results', {
      categoriaNome: categoriaNome,
    });
  };
  
  

  return (
    <div className="container">
  <div className="row">
    <h2 className="ml-5 text-color3 py-3">Buscar por tipo de Carro</h2>
    {categories.map((category) => (
      <div key={category.id} className="col-md-3">
        <div
          className="card my-3 categories-list list-group-item list-group-item-action d-flex flex-column py-2"
          onClick={() => handleCategoryClick(category.categoria)}
          style={{
            backgroundImage: `url(${category.urlImagem})`,
            backgroundPosition: 'center',
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
            borderRadius: '10px',
            height: '243px',
            alignItems: 'center',
            justifyContent: 'flex-end',
          }}
        >
          <h5>{category.categoria}</h5>
        </div>
      </div>
    ))}
  </div>
</div>

  );
};

export default Categories;
