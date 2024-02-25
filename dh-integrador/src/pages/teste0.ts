id": 1,
    "nomeUsuario": "PadraoADMIN",
    "sobrenomeUsuario": "ADMIN",
    "emailUsuario": "padraoadmin@CTD.com.br",
    "permisao": [
        {
            "idFuncao": 1,
            "nomeFuncao": "ADMIN",
            "authority": "ADMIN"
        }
    ]

    import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { useParams, useHistory } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShareNodes, faHeart, faChevronLeft } from '@fortawesome/free-solid-svg-icons';
import { DateRangePicker } from 'react-dates';
import 'react-dates/initialize';
import 'react-dates/lib/css/_datepicker.css';
import { pt } from 'date-fns/locale';
import { format, isValid } from 'date-fns';

const DetalhesProduto = () => {
  // ... Código anterior

  return (
    <>
      <Header />
      <div className="main-content">
        {/* ... Código anterior */}
                <div className="col-lg-8 col-md-8 col-sm-12">
                  <DateRangePicker
                    startDate={dateRange.startDate}
                    startDateId="startDate"
                    endDate={dateRange.endDate}
                    endDateId="endDate"
                    onDatesChange={({ startDate, endDate }) =>
                      setDateRange({ startDate, endDate })}
                    focusedInput={focusedInput}
                    onFocusChange={(focusedInput) => setFocusedInput(focusedInput)}
                    numberOfMonths={2}
                    isOutsideRange={() => false}
                    showClearDates={true}
                    keepOpenOnDateSelect={true}
                    renderMonthText={month => isValid(month) ? format(month, 'MMMM yyyy', { locale: pt }) : ''}
                    renderDayContents={day => isValid(day) ? format(day, 'd', { locale: pt }) : ''}
                  />

                </div>
                {/* ... Restante do código */}
      </div>
      <Footer />
    </>
  );
};

export default DetalhesProduto;
