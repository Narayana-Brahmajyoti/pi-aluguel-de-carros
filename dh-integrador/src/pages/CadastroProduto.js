import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { id } from 'date-fns/locale';
import api from '../services/authService';
import Select from "react-select";

const CadastroProduto = () => {
  
  const [nomeProduto, setNomeProduto] = useState('');
  const [descricaoProduto, setDescricaoProduto] = useState('');
  const [cidades, setCidades] = useState([]);
  const [idCidade, setIdCidade] = useState('');
  const [categoria, setCategoria] = useState([]);
  const [idCategoria, setIdCategoria] = useState('');
  const [imagem, setImagem] = useState([]);
  const [idImagem, setIdImagem] = useState([]);
  const [caracteristica, setCaracteristica] = useState([]);
  const [idCaracteristica, setIdCaracteristica] = useState('');
  const [caracteristicasSelecionadas, setCaracteristicasSelecionadas] = useState([]);
  const [imagens, setImagens] = useState([{ idImagem: 0, tituloImagem: '', urlImagem: '' }]);
  const [imagensSelecionadas, setImagensSelecionadas] = useState([]);
  const [urlImagem, setUrlImagem] = useState('');
  const [selectedOptions, setSelectedOptions] = useState([]);
  // const [idImagem, setIdImagem] = useState('');
  const [tituloImagem, setTituloImagem] = useState('');
  // const [location, setLocation] = useState();
  const addImagem = () => {
    setImagens([...imagens, { tituloImagem: '', urlImagem: '' }]);
  };

  useEffect(() => {
  
    async function fetchImagem() {
      try {
        const response = await api.get('/imagem');
        setImagem(response.data);
      } catch (error) {
        console.error('Erro ao buscar as imagens:', error);
      }
    }

    fetchImagem();
  }, []);

  useEffect(() => {
    const fetchCidades = async () => {
    const response = await axios.get('http://localhost:8080/cidade');
    setCidades(response.data);
    };

    fetchCidades();
  }, []);

  useEffect(() => {
    const fetchCategoria = async () => {
    const response = await axios.get('http://localhost:8080/categoria');
    setCategoria(response.data);
    };

    fetchCategoria();
  }, []);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const fetchCaracteristica = async () => {
    const response = await axios.get('http://localhost:8080/caracteristica', {
      headers: { Authorization: `Bearer ${token}` },
    });
    setCaracteristica(response.data);
    };

    fetchCaracteristica();
  }, []);

  const handleCaracteristicaChange = (e) => {
    const { value, checked } = e.target;

    if (checked) {
      setCaracteristicasSelecionadas((prevState) => [...prevState, parseInt(value, 10)]);
    } else {
      setCaracteristicasSelecionadas((prevState) => prevState.filter((id) => id !== parseInt(value, 10)));
    }
  };

  const imagemOptions = imagem.map((imagem) => ({
    value: imagem.idImagem,
    label: imagem.tituloImagem,
    key: imagem.idImagem
  }))
  


  
  const handleSelectUpdate = (selectedOptions) => {
    // event.preventDefault()
  // setSelectedOptions(event);

    console.log(selectedOptions)
    setSelectedOptions(selectedOptions)
  }
  
  const handleImagemChange = (e) => {
    const { value, selected } = e.target;

    if (selected) {
      setImagensSelecionadas((prevState) => [...prevState, parseInt(value, 10)]);
      console.log(imagensSelecionadas)
    } else {
      setImagensSelecionadas((prevState) => prevState.filter((id) => id !== parseInt(value, 10)));
    }
  };

  

  const handleSubmitImagem = async (e) => {
    e.preventDefault();
    
    const token = localStorage.getItem('token');
    
    const data = {
      tituloImagem,
      urlImagem
    }
    
    try {
      const response = await axios.post('http://localhost:8080/imagem', data, {
        headers: { Authorization: `Bearer ${token}` },
      });
      
      if (response.status === 201) {
        alert('Imagem cadastrada com sucesso!');
        window.location.reload(true) 
      } else {
        alert('Ocorreu um erro ao cadastrar a imagem. Por favor, tente novamente.');
      }
    } catch (error) {
      console.error(error);
      if (error.response) {
        if (error.response.status === 400) {
          alert('Requisição inválida. Verifique os dados inseridos.');
        } else if (error.response.status === 403) {
          alert('Você não tem permissão para realizar essa ação.');
        } else {
          alert('Ocorreu um erro ao cadastrar a imagem. Por favor, tente novamente.');
        }
      } else {
        alert('Ocorreu um erro ao cadastrar a imagem. Por favor, tente novamente.');
      }
    }
  };   

  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = localStorage.getItem('token');
    const imagensFormatadas = selectedOptions.map((option) => ({ idImagem: option.value }));
    const caracteristicasFormatadas = caracteristicasSelecionadas.map((id) => ({ idCaracteristica: id }));
    


 

    const data = {
    nomeProduto,
    descricaoProduto,
    caracteristicas: caracteristicasFormatadas,
    imagem: imagensFormatadas,
    categoria: {
      id: idCategoria,
    },
    cidade: {
      idCidade,
    }
    
    };

    console.log('Dados enviados:', data);  
    try {
      
      const response = await axios.post('http://localhost:8080/produto', data, {
        headers: { Authorization: `Bearer ${token}` },
      });
  
      if (response.status === 201) {
        alert('Produto cadastrado com sucesso!');
      } else {
        alert('Ocorreu um erro ao cadastrar o produto. Por favor, tente novamente.');
      }
    } catch (error) {
      console.error(error);
      if (error.response) {
        if (error.response.status === 400) {
          alert('Requisição inválida. Verifique os dados inseridos.');
        } else if (error.response.status === 403) {
          alert('Você não tem permissão para realizar essa ação.');
        } else {
          alert('Ocorreu um erro ao cadastrar o produto. Por favor, tente novamente.');
        }
      } else {
        alert('Ocorreu um erro ao cadastrar o produto. Por favor, tente novamente.');
      }
    }
    console.log(data);
  };
  


  // function App() {
  //   const [selectedOptions, setSelectedOptions] = useState([]);
  
  //   const handleChange = (event) => {
  //     const { value } = event.target;
  //     if (selectedOptions.includes(value)) {
  //       setSelectedOptions(selectedOptions.filter((option) => option !== value));
  //     } else {
  //       setSelectedOptions([...selectedOptions, value]);
  //     }
  //   };
  
    
  // }

  return (
    <>
      <Header />
      

      <div className="main-content">
      <div className="container">
        <div className="row">
          <div className="col-12">
            <h1 className="text-center">Cadastro de Produto</h1>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="nomeProduto">Nome do Produto:</label>
                <input
                  type="text"
                  className="form-control"
                  id="nomeProduto"
                  value={nomeProduto}
                  onChange={(e) => setNomeProduto(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="descricaoProduto">Descrição do Produto:</label>
                <textarea
                  className="form-control"
                  id="descricaoProduto"
                  rows="3"
                  value={descricaoProduto}
                  onChange={(e) => setDescricaoProduto(e.target.value)}
                  required
                ></textarea>
              </div>
              <div className="form-group">
                <label htmlFor="cidade">Cidade:</label>
                <select
                  className="form-control"
                  id="cidade"
                  value={idCidade}
                  onChange={(e) => setIdCidade(e.target.value)}
                  required
                >
                  <option value="">Selecione uma cidade</option>
                  {cidades.map((cidade) => (
                    <option key={cidade.idCidade} value={cidade.idCidade}>
                      {cidade.nomeCidade}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label htmlFor="categoria">Categoria:</label>
                <select
                  className="form-control"
                  id="categoria"
                  value={idCategoria}
                  onChange={(e) => setIdCategoria(e.target.value)}
                  required
                >
                  <option value="">Selecione uma categoria</option>
                  {categoria.map((categoria) => (
                    <option key={categoria.id} value={categoria.id}>
                      {categoria.categoria}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group">
              <label>Características:</label>
              <div className="row">
                {caracteristica.map((caracteristica) => (
                <div key={caracteristica.idCaracteristica} className="col-md-4">
                  <div className="form-check">
                    <input
                      className="form-check-input"
                      type="checkbox"
                      value={caracteristica.idCaracteristica}
                      onChange={handleCaracteristicaChange}
                      id={`caracteristica-${caracteristica.idCaracteristica}`}
                    />
                    <label className="form-check-label" htmlFor={`caracteristica-${caracteristica.idCaracteristica}`}>
                      <img src={caracteristica.iconeCaracteristica} alt={caracteristica.nomeCaracteristica} width="30" height="30" />
                      {caracteristica.nomeCaracteristica}
                    </label>
                  </div>
                </div>
              ))}
              </div>
              <div>
              <div className="form-group">
                <label for="">Imagens:</label>
              
                <Select 
                  // onChange={handleImagemChange}
                  onChange={handleSelectUpdate}
                  isMulti={true} 
                  isSearchable={true}
                  isClearable={true}
                  closeMenuOnSelect={false}
                  options={imagemOptions}
                  // value={selectedOptions}                
                />



                <label htmlFor="tituloImagem">Titulo imagem:</label>
                <input
                  type="text"
                  className="form-control"
                  id="tituloImagem"
                  value={tituloImagem}
                  onChange={(e) => setTituloImagem(e.target.value)}
                  
                />
                <label htmlFor="UrlImagem">Url da imagem:</label>
                <input
                  type="text"
                  className="form-control"
                  id="urlImagem"
                  value={urlImagem}
                  onChange={(e) => setUrlImagem(e.target.value)}
                  
                />
                <button type="button" className="btn btn-primary mb-4" onClick={handleSubmitImagem}>Adicionar Imagem</button>
                </div>
              </div> 
            
              </div>
              <button type="submit" className="btn btn-primary mt-3 bg-color1">
                Cadastrar Produto
              </button>
            </form>
          </div>
        </div>
      </div>
      </div>
      <Footer />
    </>
  );
};


export default CadastroProduto;
