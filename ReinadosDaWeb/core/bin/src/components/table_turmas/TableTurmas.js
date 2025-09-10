import React, { useState, useEffect } from 'react';
import { useProfessor } from '../../context/ProfessorContext';
import { SiGoogleclassroom } from "react-icons/si";
import { IoSearchOutline, IoPersonSharp, IoTrashBinOutline } from "react-icons/io5";
import TurmasTable from './TurmasTable';
import Button from '../button/Button';
import { Link } from 'react-router-dom';
import './TableTurmas.css';
import IconWithLabel from '../icon_with_label/IconWithLabel';

function TableTurmas({ handleNameBackground }) {
    const [turmas, setTurmas] = useState([]);
    const [turmaSelecionada, setTurmaSelecionada] = useState(null);
    const { professor } = useProfessor();

    useEffect(() => {
        handleNameBackground("Turmas Cadastradas");

        async function fetchTurmas() {
            if (professor) {
                try {
                    const response = await fetch(`https://threeano-mostra.onrender.com/api/turmas/professor/1/alunos`);
                    const data = await response.json();
                    setTurmas(data);
                    console.log("Dados recebidos:", data);
                } catch (error) {
                    console.error('Erro ao carregar as turmas:', error);
                }
            }
        }

        fetchTurmas();
    }, [professor]);

    const handleDelete = async (turmaId) => {
        try {
            const response = await fetch(`https://threeano-mostra.onrender.com/api/turmas/${turmaId}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                setTurmas(turmas.filter((turma) => turma.id !== turmaId));
                console.log("Turma deletada:", turmaId);
            } else {
                console.error('Erro ao deletar a turma:', response.statusText);
            }
        } catch (error) {
            console.error('Erro ao deletar a turma:', error);
        }
    };

    const handleSetTurmaSelecionada = (turma) => {
        setTurmaSelecionada(turma);
        handleNameBackground("Detalhes da turma");
    };

    if (turmas.length === 0) {
        return (
            <div className="zero">
                <div className="paragrafo-container">
                    <p>Não há turmas cadastradas</p>
                </div>
            </div>
        );
    } else {
        return (
            <div className="table-container">
                {!turmaSelecionada ? (
                    <TurmasTable
                        turmas={turmas}
                        onRowClick={handleSetTurmaSelecionada}
                        onDelete={handleDelete}
                        page={1}
                    />
                ) : (
                    <div className="informacoes-container">
                        <p><strong ><IconWithLabel icon={<SiGoogleclassroom />} label={`Turma: ${turmaSelecionada.turma.nomeTurma}`} /></strong></p>
                        <p><strong ><IconWithLabel icon={<IoSearchOutline />} label={`Codigo: ${turmaSelecionada.turma.codigoTurma}`} /></strong></p>
                        <p><strong ><IconWithLabel icon={<IoPersonSharp />} label={`Alunos: ${turmaSelecionada.alunoCount}`} /></strong></p>
                        <div className="inf-butom-conteiner">
                            <Button
                                id="inf-buton-red"
                                onClick={() => {
                                    handleDelete(turmaSelecionada.turma.id);
                                    setTurmaSelecionada(null);
                                }}
                            >
                                Deletar turma
                            </Button>
                        </div>
                    </div>
                )}
            </div>
        );
    }
}

export default TableTurmas;