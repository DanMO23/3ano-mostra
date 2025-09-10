// src/components/IniciarCadastro.js
import React, { useState } from 'react';
import CampField from '../camp_field/CampField';
import { FaEnvelope } from 'react-icons/fa';
import { PiPasswordFill } from 'react-icons/pi';
import { IoPersonSharp } from "react-icons/io5";
import { Link, useNavigate } from 'react-router-dom';
import { useProfessor } from '../../context/ProfessorContext';
import '../iniciar_sessao/IniciarSessao.css';
import './IniciarCadastro.css';
import PopUp from '../popup/PopUp';
import Button from '../button/Button';

function IniciarCadastro() {
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isPopUpVisible, setIsPopUpVisible] = useState(false);
    const { login } = useProfessor();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/professores', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ nome, email, senha }),
            });
    
            if (response.status === 200 || response.status === 201) {
                const data = await response.json();
                alert('Cadastro realizado com sucesso!');
                login(data);
                navigate('/management');
            } else {
                setErrorMessage('Erro ao cadastrar usuário. Tente novamente.');
                setIsPopUpVisible(true);
            }
        } catch (error) {
            console.error('Erro ao fazer cadastro:', error);
            setErrorMessage('Houve um erro ao tentar cadastrar. Tente novamente.');
            setIsPopUpVisible(true);
        }
    };
    


    return (
        <div className="formi-cadastro">
            <form onSubmit={handleSubmit} className='formi-cadastro'>
                <div>
                    <div className="input-container">
                        <div className="input-with-icon">
                            <IoPersonSharp />
                            <CampField
                                type="text"
                                name="nome"
                                id="nome"
                                className="camp"
                                placeholder="Nome"
                                value={nome}
                                onChange={(e) => setNome(e.target.value)} 
                                required
                            />
                        </div>
                    </div>
                    
                    <div className="input-container">
                        <div className="input-with-icon">
                            <FaEnvelope />
                            <CampField
                                type="email"
                                name="email"
                                id="email"
                                className="camp"
                                placeholder="Email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)} 
                                required
                            />
                        </div>
                    </div>
                    
                    <div className="input-container">
                        <div className="input-with-icon">
                            <PiPasswordFill />
                            <CampField
                                type="password"
                                name="senha"
                                id="senha"
                                className="camp"
                                placeholder="Senha"
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                            />
                        </div>
                    </div>
                </div>

                <div id='container-button-cadastro' className='container-button'>
                    <Button type="submit">Cadastrar</Button>
                </div>

            </form>

            {isPopUpVisible && (
                <PopUp
                    mensagem={errorMessage} 
                    onOk={() => setIsPopUpVisible(false)} 
                />
            )}
        </div>
    );
}

export default IniciarCadastro;
