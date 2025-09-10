import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CampField from '../camp_field/CampField';
import PopUp from '../popup/PopUp';
import Button from '../button/Button';
import { FaEnvelope } from 'react-icons/fa';
import { IoPersonSharp } from "react-icons/io5";
import './IniciarRegistro.css';
import { useAluno } from '../../context/AlunoContext';


function IniciarRegistro() {
    const [nome, setNome] = useState('');
    const [classCode, setClassCode] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isPopUpVisible, setIsPopUpVisible] = useState(false);
    const {login} = useAluno();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('https://threeano-mostra.onrender.com/api/alunos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ nome, classCode }),
            });

            if (response.ok) {
                const data = await response.json();
                console.log('Registro bem-sucedido:', data);
                login(data);
                navigate('/jogar'); // Redirecionar após o registro
            } else {
                const errorData = await response.json();
                setErrorMessage(errorData.message || 'Erro ao registrar.');
                setIsPopUpVisible(true);
            }
        } catch (error) {
            console.error('Erro ao fazer registro:', error);
            setErrorMessage('Houve um erro ao tentar registrar. Tente novamente.');
            setIsPopUpVisible(true);
        }
    };

    return (
        <div className="formi-cadastro">
            <form onSubmit={handleSubmit} className='formi-cadastro'>
                <div className="input-container">
                    <div className="input-with-icon">
                        <IoPersonSharp />
                        <CampField
                            type="text"
                            name="nome"
                            id="nome"
                            className="camp"
                            placeholder="Nome Aluno"
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
                            type="text"
                            name="classCode"
                            className="camp"
                            placeholder="Código da Turma"
                            value={classCode}
                            onChange={(e) => setClassCode(e.target.value)}
                            required
                        />
                    </div>
                </div>
                <div className='container-button'>
                    <Button type="submit">Registrar</Button>
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

export default IniciarRegistro;