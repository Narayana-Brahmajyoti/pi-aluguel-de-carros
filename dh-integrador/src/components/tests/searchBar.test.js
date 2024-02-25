import "@testing-library/jest-dom"
import { render, screen, fireEvent } from "@testing-library/react"
import SearchBar from "../SearchBar"

// jest.mock()
describe('SearchBar', () => {
    test('Deve renderizar corretamente', () => {
        render(<SearchBar/>)

        expect(screen.getByText('Buscar ofertas em carros, vans e muito mais')).toBeInTheDocument();
        expect(screen.getByText('Para onde vamos?')).toBeInTheDocument();
        expect(screen.getByText('Buscar')).toBeInTheDocument();   
    })
    
    test('Deve chamar o handleSearch quando clicar no botão buscar', () => {
        render(<SearchBar/>)

        const btnBuscar = screen.getByText('Buscar');

        fireEvent.click(btnBuscar);
        // expect()
    })

    
})