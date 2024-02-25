import React from 'react';
import Header from '../components/Header';
import SearchBar from '../components/SearchBar';
import RecomendacoesCard from '../components/RecomendacoesCard';
import Footer from '../components/Footer';
import Categories from'../components/Categories';


const Home = () => {
  return (
    <>
      <Header />
      <div className="main-content">

      <div>
        <SearchBar />
      </div>
      <div className="pt-3 px-1">
        
        {/* <Categories /> */} {/* Comente essa linha se não estiver usando Categories */}
        {/* <ProductCard /> */}
        <Categories />
      </div>
      <div className="pt-3 px-1">
        
        <RecomendacoesCard />
      </div>
      </div>

      <Footer />
    </>
  );
}

export default Home;
