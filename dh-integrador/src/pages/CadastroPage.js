import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';

const Cadastro = () => {
  const history = useHistory();
  const [formData, setFormData] = useState({
    nomeUsuario: '',
    sobrenomeUsuario: '',
    emailUsuario: '',
    senhaUsuario: '',
    confirmPassword: '',
  });

  const [errors, setErrors] = useState({
    nomeUsuario: '',
    sobrenomeUsuario: '',
    emailUsuario: '',
    senhaUsuario: '',
    confirmPassword: '',
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
    setErrors((prevState) => ({
      ...prevState,
      [name]: '',
    }));
  };

  const validateFields = () => {
    let isValid = true;
    const newErrors = {};

    if (!formData.nomeUsuario) {
      newErrors.nomeUsuario = 'O nome é obrigatório';
      isValid = false;
    }

    if (!formData.sobrenomeUsuario) {
      newErrors.sobrenomeUsuario = 'O sobrenome é obrigatório';
      isValid = false;
    }

    if (!formData.emailUsuario) {
      newErrors.emailUsuario = 'O email é obrigatório';
      isValid = false;
    }

    if (!formData.senhaUsuario) {
      newErrors.senhaUsuario = 'A senha é obrigatória';
      isValid = false;
    }

    if (!formData.confirmPassword) {
      newErrors.confirmPassword = 'A confirmação de senha é obrigatória';
      isValid = false;
    } else if (formData.senhaUsuario !== formData.confirmPassword) {
      newErrors.confirmPassword = 'As senhas não coincidem';
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!validateFields()) {
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/usuario', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error('Erro ao enviar os dados');
      }

      alert('Cadastro realizado com sucesso!');
      history.push('/login');
    } catch (error) {
      console.error('Erro:', error);
      alert('Ocorreu um erro ao realizar o cadastro. Por favor, tente novamente.');
    }
  };

  return (
    <>
      <Header />
      <div className="container">
        <div className="row justify-content-center mt-5">
          <div className="col-md-6">
            <div className="">
              <div className="card-header my-3 text-center text-color1 mb-3">
                <h1>Criar Conta</h1>
              </div>
              <div className="card-body">
                <form onSubmit={handleSubmit}>
                  <div className="form-group row">
                    <div className="col-sm-6">
                      <label htmlFor="nomeUsuario">Nome</label>
                      <input
                        type="text"
                        className={`mt-1 mb-2 form-control ${errors.nomeUsuario ? 'is-invalid' : ''}`}
                        id="nomeUsuario"
                        name="nomeUsuario"
                        value={formData.nomeUsuario}
                        onChange={handleChange}
                        required
                      />
                      {errors.nomeUsuario && <div className="invalid-feedback">{errors.nomeUsuario}</div>}
                    </div>
                    <div className="col-sm-6">
                      <label htmlFor="sobrenomeUsuario">Sobrenome</label>
                      <input
                        type="text"
                        className={`mt-1 mb-2 form-control ${errors.sobrenomeUsuario ? 'is-invalid' : ''}`}
                        id="sobrenomeUsuario"
                      name="sobrenomeUsuario"
                      value={formData.sobrenomeUsuario}
                      onChange={handleChange}
                      required
                        />
                      {errors.sobrenomeUsuario && <div className="invalid-feedback">{errors.sobrenomeUsuario}</div>}
                    </div>
                  </div>
                  <div className="form-group">
                    <label htmlFor="emailUsuario">E-mail</label>
                    <input
                      type="email"
                      className={`mt-1 mb-2 form-control ${errors.emailUsuario ? 'is-invalid' : ''}`}
                      id="emailUsuario"
                    name="emailUsuario"
                    value={formData.emailUsuario}
                    onChange={handleChange}
                    required
                        />
                    {errors.emailUsuario && <div className="invalid-feedback">{errors.emailUsuario}</div>}
                  </div>
                  <div className="form-group">
                    <label htmlFor="senhaUsuario">Senha</label>
                    <input
                      type="password"
                      className={`mt-1 mb-2 form-control ${errors.senhaUsuario ? 'is-invalid' : ''}`}
                    id="senhaUsuario"
                    name="senhaUsuario"
                    value={formData.senhaUsuario}
                    onChange={handleChange}
                    required
                        />
                    {errors.senhaUsuario && <div className="invalid-feedback">{errors.senhaUsuario}</div>}
                  </div>
                  <div className="form-group">
                    <label htmlFor="confirmPassword">Confirmar Senha</label>
                    <input
                      type="password"
                      className={`mt-1 mb-2 form-control ${errors.confirmPassword ? 'is-invalid' : ''}`}
                    id="confirmPassword"
                    name="confirmPassword"
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    required
                        />
                    {errors.confirmPassword && <div className="invalid-feedback">{errors.confirmPassword}</div>}
                  </div>
                  <div className="col-md-5 mt-1 mb-5 btn-login">
                    <button type="submit" className="text-center btn bg-color1 text-white w-100 btn-block mt-4">
                      Criar Conta
                    </button>
                  </div>
                </form>
                <p className="text-center mt-3 mb-0">
                  Já tem uma conta?{' '}
                  <Link to="/login" className="text-link">
                    Iniciar Sessão
                  </Link>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default Cadastro;


