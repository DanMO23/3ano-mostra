import React, { useState } from 'react';
import CampField from '../camp_field/CampField';
import { MdOutlineDriveFileRenameOutline } from "react-icons/md";
import { Link, useNavigate } from 'react-router-dom';
import { useProfessor } from '../../context/ProfessorContext';
import '../iniciar_sessao/IniciarSessao.css';
import './IniciarCadastroTurma.css'
import PopUp from '../popup/PopUp';
import Button from '../button/Button';

function IniciarCadastroTurma() {
    const [nomeTurma, setNome] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isPopUpVisible, setIsPopUpVisible] = useState(false);
    const navigate = useNavigate();
    const { professor } = useProfessor();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!professor) {
            setErrorMessage('Você precisa estar logado para cadastrar uma turma.');
            setIsPopUpVisible(true);
            return;
        }
    
        try {
            const codigoTurma = Math.floor(Math.random() * 9999);
            const idProfessor = professor.id; // ID do professor logado
            const json = { nomeTurma, codigoTurma, idProfessor }; // Inclui o ID do professor
            console.log(json);
            const response = await fetch('https://threeano-mostra.onrender.com/api/turmas', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(json),
            });
    
            if (response.ok) {
                alert('Cadastro realizado com sucesso!');
                navigate('/management');
            } else {
                const errorData = await response.json();
                setErrorMessage(errorData.message || 'Erro ao cadastrar a turma. Tente novamente.');
                setIsPopUpVisible(true);
            }
        } catch (error) {
            console.error('Erro ao fazer cadastro:', error);
            setErrorMessage('Houve um erro ao tentar cadastrar. Tente novamente.');
            setIsPopUpVisible(true);
        }
    };

    return (
        <div className="formi-turma">
            <form onSubmit={handleSubmit}>
                <div className="input-container" id="input-cadastro-container">
                    {professor ? (
                        <div className="input-with-icon">
                            <MdOutlineDriveFileRenameOutline />
                            <CampField
                                type="text"
                                name="nome"
                                id="nome"
                                className="camp"
                                placeholder="Nome"
                                value={nomeTurma}
                                onChange={(e) => setNome(e.target.value)}
                            />
                        </div>
                    ) : (
                        <p>Você precisa estar logado para cadastrar uma turma.</p>
                    )}
                    {professor && (
                        <div id="container-button-cadastro" className="container-button">
                            <Button type="submit">Cadastrar</Button>
                        </div>
                    )}
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

export default IniciarCadastroTurma;