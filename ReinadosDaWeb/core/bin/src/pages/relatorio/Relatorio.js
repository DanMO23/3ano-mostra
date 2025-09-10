import React, { useState, useEffect } from 'react';
import { IoGameController } from "react-icons/io5";
import { MdWatchLater } from "react-icons/md";
import { SiGoogleclassroom } from "react-icons/si";
import { BsFillPersonFill } from "react-icons/bs";
import './Relatorio.css';
import { useProfessor } from './../../context/ProfessorContext';
import TurmasTable from '../../components/table_turmas/TurmasTable';
import ContainerCards from './../../components/progress_card/ContainerCards';
import Button from '../../components/button/Button';
import AlunosTable from './../../components/table_aluno/AlunosTable';

function Relatorio() {
    const [detalhesTurma, setDetalhesTurma] = useState([]);
    const [turmaSelecionada, setTurmaSelecionada] = useState(null);
    const { professor } = useProfessor();
    const [alunos, setAlunos] = useState([
        { id: 1, nome: 'João Silva' },
        { id: 2, nome: 'Maria Oliveira' },
        { id: 3, nome: 'Pedro Santos' },
        { id: 4, nome: 'Ana Costa' },
        { id: 5, nome: 'Lucas Pereira' },
    ]);
    const [alunoSelecionado, setAlunoSelecionado] = useState(null);
    const [mostrarAlunos, setMostrarAlunos] = useState(false);
    const [mostrarPartidas, setMostrarPartidas] = useState(false);
    const [detalhesPartidaAluno, setDetalhesPartidaAluno] = useState([]);
    const [erroRelatorio, setErroRelatorio] = useState(false);
    const [isLoading, setIsLoading] = useState(true);
    const [relatorioTurma, setRelatorioTurma] = useState({});
    const [estatisticasTurma, setEstatisticasTurma] = useState({
        totalJogos: 0,
        tempoMedioJogos: 0,
        mediasScores: {
            SEGURANCA: 0,
            CONTATO: 0,
            CONTEUDO: 0,
        },
    });
    

    const handleRowClick = (aluno) => {
        setAlunoSelecionado(aluno);
        setMostrarPartidas(true);
    };

    const calcularEstatisticasTurma = (relatorioTurma) => {
        let totalJogos = 0;
        let totalDuracao = 0;
        let somaScores = {
            CONTEUDO: 0,
            CONTATO: 0,
            SEGURANCA: 0
        };
        let contagemScores = {
            CONTEUDO: 0,
            CONTATO: 0,
            SEGURANCA: 0
        };
    
        for (let i = 0; i < relatorioTurma.relatoriosAlunos.length; i++) {
            let relatorioAluno = relatorioTurma.relatoriosAlunos[i];
    
            for (let j = 0; j < relatorioAluno.partidas.length; j++) {
                let partida = relatorioAluno.partidas[j];
    
                // Contar o número total de jogos
                totalJogos++;
    
                // Somar a duração total das partidas
                totalDuracao += partida.duracaoPartida;
    
                // Somar os scores por categoria
                for (let k = 0; k < partida.scores.length; k++) {
                    let score = partida.scores[k];
                    if (somaScores.hasOwnProperty(score.tipoScore)) {
                        somaScores[score.tipoScore] += score.valorScore;
                        contagemScores[score.tipoScore]++;
                    }
                }
            }
        }
    
        // Calcular o tempo médio jogado
        let tempoMedioJogos = totalJogos > 0 ? totalDuracao / totalJogos : 0;
    
        // Calcular as médias de pontuação por categoria
        let mediasScores = {};
        for (let tipo in somaScores) {
            mediasScores[tipo] = contagemScores[tipo] > 0 ? somaScores[tipo] / contagemScores[tipo] : 0;
        }
    
        return {
            totalJogos: totalJogos,
            tempoMedioJogos: tempoMedioJogos,
            mediasScores: mediasScores
        };
    };
       

    useEffect(() => {
        async function fetchRelatorioTurma() {
            setIsLoading(false)
            if (turmaSelecionada && turmaSelecionada.id) {
                try {
                    const response = await fetch(`https://threeano-mostra.onrender.com/api/relatorios/turma/${turmaSelecionada.id}`);
                    if (response.ok) {
                        const data = await response.json();
                        console.log("Dados do relatório recebidos do backend:", data);
                        setRelatorioTurma(data || {});
                        setErroRelatorio(false); // Sucesso na requisição
                        const stats = calcularEstatisticasTurma(data);
                        console.log("Estatísticas calculadas:", stats);
                        setEstatisticasTurma(stats);
                    } else {
                        console.error('Erro ao carregar os dados do relatório:', response.statusText);
                        setErroRelatorio(true); // Erro na resposta
                    }
                } catch (error) {
                    console.error('Erro ao carregar os dados do relatório:', error);
                    setErroRelatorio(true); // Erro na requisição
                }
            }
            setIsLoading(true)
        }

        fetchRelatorioTurma();
    }, [turmaSelecionada]);


    useEffect(() => {
        async function fetchTurmas() {
            if (professor) {
                try {
                    const response = await fetch(`https://threeano-mostra.onrender.com/api/turmas/professor/${professor.id}`);
                    const data = await response.json();
                    console.log("Dados recebidos do backend:", data);
                    setDetalhesTurma(data || []);
                } catch (error) {
                    console.error('Erro ao carregar as turmas:', error);
                }
            }
        }

        fetchTurmas();
    }, [professor]);

    const handleTurmaClick = (turma) => {
        setTurmaSelecionada(turma);
    };

    const handleClickAluno = () => {
        setMostrarAlunos(true);
    };

    const LoadingSpinner = () => (
        <div className="loading-container">
            <div className="spinner"></div>
            <p>Carregando...</p>
        </div>
    );

    return (
        <>
            {mostrarAlunos ? (
                !mostrarPartidas ? (
                    <div className="table-container">
                        <AlunosTable alunos={alunos} onRowClick={handleRowClick} />
                    </div>
                ) : (
                    <>
                        <div className='title-class'>
                            <BsFillPersonFill className="icon-title" />
                            <h1>{alunoSelecionado.nome}</h1>
                        </div>
                        <div className="container-relatorios">
                            <div className="info-games-container">
                                <div className="info-games">
                                    <div className="info-games-title">
                                        <IoGameController className="icon-games" />
                                        <h2>1 º Partida</h2>
                                    </div>
                                    <div className="info-games-title">
                                        <MdWatchLater className="icon-games" />
                                        <h2>Tempo:</h2>
                                        <h2>5 min</h2>
                                    </div>

                                    <div className="info-games-matchs">
                                        <ContainerCards />
                                    </div>
                                </div>
                            </div>
                            <Button onClick={handleClickAluno}>Próxima Partida</Button>
                        </div>
                    </>
                )
            ) : (
                !isLoading ? (
                    <LoadingSpinner />
                ) : (
                    !turmaSelecionada ? (
                        detalhesTurma.length > 0 ? (
                            <div className="table-container">
                                <TurmasTable
                                    turmas={detalhesTurma}
                                    onRowClick={handleTurmaClick}
                                    page={0}
                                />
                            </div>
                        ) : (
                            <p>Carregando turmas...</p>
                        )
                    ) : erroRelatorio ? (
                        <div className="container-relatorios">
                            <p className="erro-mensagem">Nenhuma partida foi realizada para esta turma.</p>
                        </div>
                    ) : (
                        <>
                            <div className='title-class'>
                                <SiGoogleclassroom className="icon-title" />
                                <h1>{alunoSelecionado ? alunoSelecionado.nome : "aaa"}</h1>
                            </div>
                            <div className="container-relatorios">
                                <div className="info-games-container">
                                    <div className="info-games">
                                        <div className="info-games-title">
                                            <IoGameController className="icon-games" />
                                            <h2>Partidas:</h2>
                                            <h2>{estatisticasTurma.totalJogos}</h2>
                                        </div>
                                        <div className="info-games-title">
                                            <MdWatchLater className="icon-games" />
                                            <h2>Tempo Médio:</h2>
                                            <h2>{estatisticasTurma.tempoMedioJogos}</h2>
                                        </div>

                                        <div className="info-games-matchs">
                                            <ContainerCards 
                                            score1={estatisticasTurma.mediasScores.CONTATO}
                                            score2={estatisticasTurma.mediasScores.SEGURANCA} 
                                            score3={estatisticasTurma.mediasScores.CONTEUDO}/>
                                        </div>
                                    </div>
                                </div>
                                <Button onClick={handleClickAluno}>Visualizar Alunos</Button>
                            </div>
                        </>
                    )
                )
            )}
        </>
    );
}

export default Relatorio;
