// ProtectedRoute.js
import React from 'react';
import { Route, Redirect } from 'react-router-dom';

const ProtectedRoute = ({ component: Component, ...rest }) => {
  const isAuthenticated = () => {
    const token = localStorage.getItem('token');

    if (token) {
      // Aqui você pode adicionar validações adicionais, como verificar a expiração do token
      return true;
    } else {
      return false;
    }
  };

  return (
    <Route
      {...rest}
      render={(props) => (
        isAuthenticated() ? <Component {...props} /> : <Redirect to="/login" />
      )}
    />
  );
};

export default ProtectedRoute;
