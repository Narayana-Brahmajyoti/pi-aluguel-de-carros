import React from 'react';
import { FaFacebook, FaTwitter, FaInstagram, FaLinkedin } from 'react-icons/fa';

const Footer = () => {
  return (
    <footer className="footer mt-auto py-3 bg-color1">
      <div className="container">
        <div className="row">
          <div className="col-md-6">
            <p className="text-white">&copy; {new Date().getFullYear()} Vrum Vrum Aluguel de Carros. Todos os direitos reservados.</p>
          </div>
          <div className="col-md-6 d-flex justify-content-end align-items-center">
            <a href="https://www.facebook.com" className="mx-3 icon-social text-white mr-3"><FaFacebook /></a>
            <a href="https://www.linkedin.com" className="mx-3 icon-social text-white"><FaLinkedin /></a>
            <a href="https://www.twiiter.com" className="mx-3 icon-social text-white mr-3"><FaTwitter /></a>
            <a href="https://instagram.com" className="mx-3 icon-social text-white mr-3"><FaInstagram /></a>
           
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;