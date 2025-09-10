import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { FaEnvelope } from 'react-icons/fa';
import { PiPasswordFill } from 'react-icons/pi';
import CampField from '../camp_field/CampField';
import { useProfessor } from '../../context/ProfessorContext';
import PopUp from '../popup/PopUp';
import Button from '../button/Button';
import './IniciarSessao.css';

function IniciarSessao({ setIsCadastro }) {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isPopUpVisible, setIsPopUpVisible] = useState(false);
    const navigate = useNavigate();
    const { login } = useProfessor();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('https://threeano-mostra.onrender.com/api/professores/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, senha }),
            });
    
            console.log('status da resposta', response.status);
    
            if (response.ok) {
                const data = await response.json();
                console.log('Dados do login:', data);
                login(data); // Armazena o estado do usuário ou o token
                navigate('/management'); // Redireciona para a página de gerenciamento
            } else {
                const errorData = await response.json();
                console.error('Erro no login:', errorData);
                setErrorMessage(errorData.message || 'Email ou senha incorretos.');
                setIsPopUpVisible(true);
            }
        } catch (error) {
            console.error('Erro ao fazer login:', error);
            setErrorMessage('Houve um erro ao tentar fazer login. Tente novamente.');
            setIsPopUpVisible(true);
        }
    };

    const handleTelaLogin = () => {
        setIsCadastro(true);
    };

    return (
        <div className="formi-login">
            <form onSubmit={handleSubmit} className='formi-login'>
                <div className="input-login">
                    <div className="input-container">
                        <div className="input-with-icon">
                            <div className='icon-login'>
                                <FaEnvelope />
                            </div>
                            <CampField
                                type="email"
                                name="email"
                                id="email"
                                className="camp"
                                placeholder="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>
                    </div>
                    <div className="input-container">
                        <div className="input-with-icon">
                            <div className='icon-login'>
                                <PiPasswordFill />
                            </div>
                            <CampField
                                type="password"
                                name="senha"
                                className="camp"
                                placeholder="senha"
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                            />
                        </div>
                    </div>
                </div>
                <div className='container-button' id='container-button-login'>
                    <Button onClick={handleSubmit}>Login</Button>
                    <Button onClick={handleTelaLogin}>Cadastro</Button>
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

export default IniciarSessao;