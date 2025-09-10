import React, { useEffect, useState, useRef, useCallback } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Header from '../../components/componentes_jogar/header/Header';
import Conteudo from '../../components/componentes_jogar/conteudo/Conteudo';
import CircularProgress from '@mui/material/CircularProgress';
import axios from 'axios';
import './Jogar.css';
import { useAluno } from '../../context/AlunoContext';

function Jogar() {
   const [loading, setLoading] = useState(true);
   const [gameData, setGameData] = useState(null);
   const [arcoAtual, setArcoAtual] = useState(0);
   const [gameOver, setGameOver] = useState(false);
   const [arcoConcluido, setArcoConcluido] = useState(false);
   const [feedback, setFeedback] = useState(null);
   const [atributos, setAtributos] = useState({
      CONTEUDO: 100,
      CONDUTA: 100,
      CONTATO: 100,
   });
   const [dadosPartida, setDadosPartida] = useState({});
   const location = useLocation();
   const audioRef = useRef(new Audio('/assets/audio/save-as-115826.mp3'));
   const startTime = useRef(new Date());
   const { aluno } = useAluno();
   const navigate = useNavigate();

   useEffect(() => {
      if (location.pathname === '/jogar') {
         if (audioRef.current.paused) {
            audioRef.current.play().catch(error => {
               console.error("Erro ao tentar tocar o áudio:", error);
            });
         }
      }

      return () => {
         audioRef.current.pause();
         audioRef.current.currentTime = 0;
      };
   }, [location.pathname]);

   useEffect(() => {
      const fetchGameData = async () => {
         setLoading(true);
         try {
            const response = await axios.get('http://localhost:8080/api/jogo/arcos');
            setGameData(response.data);
            console.log(response.data);
         } catch (error) {
            console.error('Erro ao buscar dados:', error);
         } finally {
            setLoading(false);
         }
      };

      fetchGameData();
   }, []);

   const calcularAjuste = (nivelCarta, resposta) => {
      const ajustes = {
         LEVE: resposta ? 5 : -10,
         MEDIO: resposta ? 10 : -25,
         PESADO: resposta ? 15 : -35,
      };
      return (ajustes[nivelCarta] || 0);
   };

   const handleAtributo = (tipoCarta, nivelCarta, resposta, idCarta, ladoEscolhido) => {
      const ajuste = calcularAjuste(nivelCarta, resposta);
      setAtributos(prev => {
         const novoValor = Math.max(0, Math.min(100, prev[tipoCarta] + ajuste));
         return { ...prev, [tipoCarta]: novoValor };
      });

      setDadosPartida(prev => {
         const arcoKey = `arco${arcoAtual + 1}`;
         const novoArco = prev[arcoKey] || { decisao: [] };
         return {
            ...prev,
            [arcoKey]: {
               ...novoArco,
               decisao: [
                  ...novoArco.decisao,
                  { idCarta, resposta, ladoEscolhido }, // Armazena o lado escolhido
               ],
            },
         };
      });
   };

   const handleArco = useCallback(() => {
      const scoreMedio = (atributos.CONTEUDO + atributos.CONDUTA + atributos.CONTATO) / 3;

      const feedbackAtual = gameData[arcoAtual].conjuntoFeedback.find(fb =>
         (scoreMedio > 85 && fb.feedbackTipo === "POSITIVO") ||
         (scoreMedio <= 85 && fb.feedbackTipo === "NEGATIVO")
      );

      setFeedback(feedbackAtual ? feedbackAtual.descricao : null);

      if (arcoAtual === gameData.length - 1) {
         setGameOver(true);
         enviarDadosPartida();
      } else {
         setArcoConcluido(true);
      }
   }, [arcoAtual, atributos, gameData]);

   const continuarJogo = () => {
      setArcoConcluido(false);
      setArcoAtual(prev => prev + 1);
   };

   const restartGame = async () => {
      setArcoAtual(0);
      setAtributos({ CONTEUDO: 100, CONDUTA: 100, CONTATO: 100 });
      setGameOver(false);
      setArcoConcluido(false);
      setDadosPartida({});
   };

   const enviarDadosPartida = async () => {
      const dadosParaEnviar = {
         usuarioId: aluno.id,
         arcosConcluidos: [],
         atributosFinais: { ...atributos },
         feedback,
         dadosPorArco: [],
         duracao: calculateGameDuration(),
      };

      for (let i = 0; i <= arcoAtual; i++) {
         const arco = {
            arcoId: i + 1,
            decisoes: dadosPartida[`arco${i + 1}`]?.decisao || [],
         };

         dadosParaEnviar.arcosConcluidos.push(arco.arcoId);
         dadosParaEnviar.dadosPorArco.push(arco);
      }

      console.log(dadosParaEnviar);

      try {
         await axios.post('http://localhost:8080/api/jogo/salvar', dadosParaEnviar);
         console.log('Dados enviados com sucesso!', dadosParaEnviar);
      } catch (error) {
         console.error('Erro ao enviar dados:', error);
      }
   };

   const calculateGameDuration = () => {
      return Math.floor((new Date() - startTime.current) / 1000);
   };

   const goToHomePage = () => {
      navigate('/registrar');
   };

   return (
      <div className="screen-container">
         {loading ?
            (<div className="game-container"> <CircularProgress /> </div>)
            : gameData ? (
               <div className="game-container">
                  <Header atributos={atributos} />
                  <Conteudo
                     gameData={gameData[arcoAtual]}
                     handleAtributo={handleAtributo}
                     handleArco={handleArco}
                  />

                  {feedback && arcoConcluido ? (
                     <div className="feedback-overlay">
                        <div className="feedback-message">
                           <h2>Feedback</h2>
                           <p>{feedback}</p>
                           <button onClick={() => setFeedback(null)}>Continuar</button>
                        </div>
                     </div>
                  ) : (
                     arcoConcluido && (
                        <div className="arco-concluido-overlay">
                           <div className="arco-concluido-message bounce-animation">
                              <h2>Arco Concluído!</h2>
                              <p>
                                 Você concluiu o arco {gameData[arcoAtual].nome} e seu Score foi:
                              </p>
                              <ul>
                                 <li>CONTEUDO: {atributos.CONTEUDO}</li>
                                 <li>CONDUTA: {atributos.CONDUTA}</li>
                                 <li>CONTATO: {atributos.CONTATO}</li>
                              </ul>
                              <button onClick={continuarJogo}>Continuar</button>
                           </div>
                        </div>
                     )
                  )}

                  {gameOver && (
                     <div className="game-over-overlay">
                        <div className="game-over-message">
                           <h2>Fim do Jogo!</h2>
                           <p>Você completou todos os arcos.</p>
                           <button onClick={restartGame}>Jogar Novamente</button>
                           <button onClick={goToHomePage} style={{ marginLeft: '10px' }}> {/* Adicionando margin-left */}
                              Voltar para a Tela Inicial
                           </button>
                        </div>
                     </div>
                  )}
               </div>
            ) : (
               <div className="game-container">Erro ao carregar</div>
            )}
      </div>
   );
}

export default Jogar;