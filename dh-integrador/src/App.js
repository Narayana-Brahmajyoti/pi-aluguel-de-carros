import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from './pages/Home';
import DetalhesProduto from './pages/DetalhesProduto';
import Login from './pages/LoginPage';
import Cadastro from './pages/CadastroPage';
import Resultados from './pages/Resultados';
import CadastroProduto from './pages/CadastroProduto';
import AdminPage from './pages/AdminPage';
import Produtos from './pages/Produtos';
import ReservarUser from './pages/ReservarUser';
import CategoryResults from './components/CategoryResults';
import ReservaPage from "./pages/ReservarPage";




import ProtectedRoute from './components/ProtectedRoute';

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/produto/:idProduto" component={DetalhesProduto} />
        <Route exact path="/login" component={Login} />
        <Route exact path="/cadastro" component={Cadastro} />
        <Route exact path="/CadastroProduto" component={CadastroProduto} />
        <Route path="/produtos" component={Produtos} />
        <ProtectedRoute exact path="/admin" component={AdminPage} />
        <Route path="/resultados" component={Resultados} />
        <Route path="/category-results" component={CategoryResults} />
        <Route path="/reserva/:idProduto" exact component={ReservaPage} />;
        <Route path="/reservas" component={ReservarUser} />
b navBarMenu

      </Switch>
    </Router>
  );
}

export default App;
