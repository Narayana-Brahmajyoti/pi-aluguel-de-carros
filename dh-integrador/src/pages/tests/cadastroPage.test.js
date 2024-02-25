import "@testing-library/jest-dom"
import { render, screen, fireEvent } from "@testing-library/react"
import Cadastro from "../CadastroPage"


describe('CadastroPage', () =>{
    test('Fluxo de cadastro', () => {    
        render(<Cadastro/>)

        expect(screen.getMyText('Criar conta')).toBeInTheDocument();
        expect(screen.getByText('Iniciar sessão')).toBeInTheDocument();
    }) 

    test('Deve renderizar texto criar conta', () => {
        screen.getByRole('heading', {
            name: /criar conta/i
        })
    })

    
});
