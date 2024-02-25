import React, { useRef } from 'react';
import logo from '../assets/vrumvrum-dh.png';
import { useHistory, useLocation } from 'react-router-dom';
import { Collapse } from 'bootstrap';

const Header = () => {
  const history = useHistory();
  const location = useLocation();
  const isLoggedIn = localStorage.getItem('token');
  const userName = localStorage.getItem('nomeUsuario');
  const userEmail = localStorage.getItem('emailUsuario');
  
  const navBarToggle = useRef(null);
  const navBarMenu = useRef(null);

  const handleLoginButtonClick = () => {
    window.location.href = '/login';
  };

  const handleSignUpButtonClick = () => {
    window.location.href = '/cadastro';
  };
  const handleReservasButtonClick = () => {
    history.push('/reservas');
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('nomeUsuario');
    localStorage.removeItem('emailUsuario');
    localStorage.removeItem('userId');

    window.location.href = '/';
  };

  const handleAdminButtonClick = () => {
    history.push('/cadastroproduto');
  };

  const toggleNavbar = () => {
    const bsCollapse = new Collapse(navBarMenu.current, {
      toggle: false,
    });
    bsCollapse.toggle();
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <a className="px-3 navbar-brand" href="/">
        <img src={logo} width="236" height="50" className="d-inline-block align-top" alt="Logo" />
        <h5 className="text-end">aluguel de carros</h5>
      </a> O caminho para a sua aventura começa aqui
      <button
        ref={navBarToggle}
        className="navbar-toggler"
        type="button"
        onClick={toggleNavbar}
      >
        <span className="navbar-toggler-icon"></span>
      </button>
      <div ref={navBarMenu} className="collapse navbar-collapse" id="navbarNav">
        <ul className="navbar-nav ml-auto align-items-center">
          {!isLoggedIn && (
            <>
              {location.pathname !== '/cadastro' && (
                <li className="nav-item p-2">
                  <button
                    onClick={handleSignUpButtonClick}
                    type="button"
                    className="btn btn-outline-success px-5"
                  >
                    Criar conta
                  </button>
                </li>
              )}
              {location.pathname !== '/login' && (
                <li className="nav-item p-2">
                  <button
                    onClick={handleLoginButtonClick}
                    type="button"
                    className="btn btn-outline-success px-5"
                  >
                    Iniciar sessão
                  </button>
                </li>
              )}
            </>
          )}
          {isLoggedIn && userEmail === 'padraoadmin@CTD.com.br' && (
<>
<li className="nav-item px-2">
<span className="navbar-text">Olá, Administrador</span>
</li>
<li className="nav-item px-2">
<button
               onClick={handleAdminButtonClick}
               type="button"
               className="btn btn-primary bg-color1 px-5"
             >
Administrar
</button>
</li>
<li className="nav-item px-2">
<button
               onClick={handleLogout}
               type="button"
               className="btn btn-outline-danger px-5"
             >
Logout
</button>
</li>
</>
)}
{isLoggedIn && userEmail !== 'padraoadmin@CTD.com.br' && (
<>
<li className="nav-item px-2">
<span className="navbar-text">Olá, {userName}</span>
</li>
<li className="nav-item px-2">
<button
               onClick={handleReservasButtonClick}
               type="button"
               className="btn btn-primary bg-color1 px-5"
             >
Minhas Reservas
</button>
</li>
<li className="nav-item px-2">
<button
               onClick={handleLogout}
               type="button"
               className="btn btn-outline-danger px-5"
             >
Logout
</button>
</li>
</>
)}
</ul>
</div>
</nav>
);
};

export default Header;
