import React, { useState, useEffect } from "react";
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMapMarkerAlt, faCalendarAlt, faLocationDot } from '@fortawesome/free-solid-svg-icons';
import { DateRangePicker } from 'react-date-range';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import { pt } from 'date-fns/locale';

const SearchBar = () => {
  const [selectedCity, setSelectedCity] = useState("");
  const [selectedDate, setSelectedDate] = useState({
    startDate: null,
    endDate: null,
    key: 'selection',
  });
  const [isOpen, setIsOpen] = useState(false);
  const [cities, setCities] = useState([]);
  const history = useHistory();

  useEffect(() => {
    const fetchCities = async () => {
      try {
        const response = await axios.get('http://localhost:8080/cidade');
        setCities(response.data);
      } catch (error) {
        console.error('Erro ao buscar cidades:', error);
      }
    };

    fetchCities();
  }, []);

  const handleCityChange = (e) => {
    setSelectedCity(e.target.value);
  };

  const handleDateRangeChange = (ranges) => {
    setSelectedDate(ranges.selection);
  };

  const handleSearch = () => {
    const city = cities.find(city => city.idCidade === parseInt(selectedCity));
    history.push('/resultados', { selectedCity, cityName: city && city.nomeCidade });
  };

  const toggleOpen = () => {
    setIsOpen(!isOpen);
  };

  const formatDate = (date) => {
    return date ? date.toLocaleDateString('pt-BR') : '';
  };

  const dateRangeText = selectedDate.startDate && selectedDate.endDate
    ? `${formatDate(selectedDate.startDate)} - ${formatDate(selectedDate.endDate)}`
    : 'Check-in - Check-out';

  return (
    <div className="pt-1 pb-3 search-bar bg-color2">
      <div className="container">
        <div className="row justify-content-center mt-5">
          <h1 className="text-white text-center pb-5">Buscar ofertas em carros, vans e muito mais</h1>
          <div className="col-sm-12 col-md-6 col-lg-5 mb-3">
            <select
              className="form-select"
              aria-label="Selecione uma cidade"
              value={selectedCity}
              onChange={handleCityChange}
            >
              <option value=""><FontAwesomeIcon icon={faMapMarkerAlt} />
              &nbsp;Para onde vamos?</option>
              {cities.map(city => (
                <option key={city.idCidade} value={city.idCidade}> <FontAwesomeIcon icon={faLocationDot} /> {city.nomeCidade}</option>
              ))}
            </select>
          </div>
          <div className="col-sm-12 col-md-6 col-lg-5 mb-3">
            <div className="form-control date-range-input" onClick={toggleOpen}>
              <FontAwesomeIcon icon={faCalendarAlt} />
              &nbsp;{dateRangeText}
            </div>
            {isOpen && (
              <DateRangePicker
                ranges={[selectedDate]}
                onChange={handleDateRangeChange}
                locale={pt}
                months={2}
                direction="horizontal"
                showDateDisplay={false}
                staticRanges={[]}
                inputRanges={[]}
                minDate={new Date()}
                onClose={() => setIsOpen(false)}
              />
            )}
          </div>
          <div className="col-sm-12 col-md-6 col-lg-2 mb-3">
            <button
              className="btn btn-primary bg-color1 w-100"
              onClick={handleSearch}
              disabled={!selectedCity}
            >
              Buscar
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchBar;

               
