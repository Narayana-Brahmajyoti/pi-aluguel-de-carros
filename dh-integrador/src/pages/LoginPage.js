import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';

const Login = () => {
  const [emailUsuario, setEmailUsuario] = useState('');
  const [senhaUsuario, setSenhaUsuario] = useState('');
  const [emailError, setEmailError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const history = useHistory();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!emailUsuario) {
      setEmailError('O email é obrigatório');
    } else {
      setEmailError('');
    }

    if (!senhaUsuario) {
      setPasswordError('A senha é obrigatória');
    } else if (senhaUsuario.length < 6) {
      setPasswordError('A senha deve ter pelo menos 6 caracteres');
    } else {
      setPasswordError('');
    }

    try {
      const response = await fetch('http://localhost:8080/auth', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ emailUsuario, senhaUsuario }),
      });

      if (!response.ok) {
        throw new Error('Erro ao enviar os dados');
      }

      const {idUsuario, token, nomeUsuario, permissao } = await response.json();
      localStorage.setItem('token', token);
      localStorage.setItem('userId', idUsuario);
      localStorage.setItem('nomeUsuario', nomeUsuario);
      localStorage.setItem('emailUsuario', emailUsuario);
      localStorage.setItem('permissao', permissao);

      if (emailUsuario === 'padraoadmin@CTD.com.br') {
        history.push('/cadastroproduto');
      } else {
        history.push('/'); // Redireciona para a página inicial após o login bem-sucedido
      }
    } catch (error) {
      console.error('Erro:', error);
      alert('Ocorreu um erro ao realizar o login. Por favor, tente novamente.');
    }
  };

  return (
    <>
      <Header />
      <div className="main-content">
        <div className="container">
          <div className="row justify-content-center">
            <div className="col-md-6 mt-5">
              <h1 className="text-center text-color1 mb-4 mt-5">Iniciar sessão</h1>
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="emailUsuario">E-mail</label>
                  <input
                    type="email"
                    className={`my-3 form-control ${emailError ? 'is-invalid' : ''}`}
                    id="emailUsuario"
                    placeholder="Digite seu email"
                    value={emailUsuario}
                    onChange={(e) => setEmailUsuario(e.target.value)}
                  />
                  {emailError && <div className="invalid-feedback">{emailError}</div>}
                </div>
                <div className="form-group">
                  <label htmlFor="senhaUsuario">Senha</label>
                  <input
                    type="password"
                    className={`my-3 form-control ${passwordError ? 'is-invalid' : ''}`}
                    id="senhaUsuario"
                    placeholder="Digite sua senha"
                    value={senhaUsuario}
                    onChange={(e) => setSenhaUsuario(e.target.value)}
                  />
                  {passwordError && <div className="invalid-feedback">{passwordError}</div>}
                </div>
                <div className="col-md-5 mt-1 mb-5 btn-login">
              <button type="submit" className="text-center btn bg-color1 text-white w-100 btn-block mt-4">Entrar</button>
            </div>
          </form>
          <p className="text-center mt-4">
            Ainda não tem conta? <Link to="/cadastro" className="text-link">Registre-se.</Link>
          </p>
        </div>
      </div>
    </div>
  </div>
  <Footer />
</>
);
};

export default Login;