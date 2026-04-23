import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { useGameStore } from '../store/useGameStore';
import { X, CheckCircle, ShieldAlert } from 'lucide-react';
import './ChallengeModal.css';

const CHALLENGES = {
  1: {
    title: 'Desafio da Floresta',
    description: 'Um estranho pediu sua senha para te dar moedas grátis no jogo. O que você faz?',
    options: [
      { text: 'Dou a senha, quero as moedas!', isCorrect: false },
      { text: 'Aviso um adulto e bloqueio a pessoa.', isCorrect: true }
    ],
    reward: { id: 1, type: 'escudo', name: 'Escudo de Privacidade' },
    nextZoneId: 2
  },
  2: {
    title: 'Desafio da Montanha',
    description: 'Você achou um link brilhante prometendo o novo jogo de graça. Você clica?',
    options: [
      { text: 'Não clico, pode ser um vírus!', isCorrect: true },
      { text: 'Clico rápido antes que suma!', isCorrect: false }
    ],
    reward: { id: 2, type: 'lupa', name: 'Lupa Antivírus' },
    nextZoneId: 3
  },
  3: {
    title: 'Desafio da Vila',
    description: 'Você quer postar uma foto com a camisa da sua escola mostrando onde você estuda.',
    options: [
      { text: 'Posto, todo mundo faz isso.', isCorrect: false },
      { text: 'Não posto, é perigoso mostrar onde estudo na internet.', isCorrect: true }
    ],
    reward: { id: 3, type: 'estrela', name: 'Estrela do Cidadão Digital' },
    nextZoneId: null 
  }
};

export default function ChallengeModal() {
  const { currentChallenge, closeChallenge, unlockZone, addItemToInventory } = useGameStore();
  const [showResult, setShowResult] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);

  if (!currentChallenge) return null;

  const challengeData = CHALLENGES[currentChallenge];

  const handleOptionClick = async (isCorrect) => {
    setIsSuccess(isCorrect);
    setShowResult(true);

    if (isCorrect) {
      await addItemToInventory(challengeData.reward);
      if (challengeData.nextZoneId) {
        await unlockZone(challengeData.nextZoneId);
      }
      setTimeout(() => {
        closeChallenge();
        setShowResult(false);
      }, 3000);
    } else {
      setTimeout(() => {
        setShowResult(false);
      }, 2500);
    }
  };

  return (
    <AnimatePresence>
      <div className="modal-overlay pointer-events-auto">
        <motion.div 
          className="modal-content glass-panel"
          initial={{ scale: 0.5, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          exit={{ scale: 0.5, opacity: 0 }}
          transition={{ type: 'spring', damping: 15 }}
        >
          <button className="close-btn" onClick={closeChallenge}>
            <X size={24} />
          </button>

          {!showResult ? (
            <div className="challenge-view">
              <h2>{challengeData?.title || 'Desafio'}</h2>
              <p className="description">{challengeData?.description || 'Carregando desafio...'}</p>
              
              <div className="options-container">
                {challengeData?.options.map((opt, idx) => (
                  <motion.button
                    key={idx}
                    className="option-btn"
                    whileHover={{ scale: 1.05 }}
                    whileTap={{ scale: 0.95 }}
                    onClick={() => handleOptionClick(opt.isCorrect)}
                  >
                    {opt.text}
                  </motion.button>
                ))}
              </div>
            </div>
          ) : (
            <motion.div 
              className="result-view"
              initial={{ scale: 0.8, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
            >
              {isSuccess ? (
                <>
                  <CheckCircle size={80} color="var(--color-tertiary)" />
                  <h2>Muito bem!</h2>
                  <p>Você ganhou: <strong>{challengeData?.reward.name}</strong></p>
                </>
              ) : (
                <>
                  <ShieldAlert size={80} color="var(--color-danger)" />
                  <h2>Ops, cuidado!</h2>
                  <p>Isso não é seguro. Tente novamente!</p>
                </>
              )}
            </motion.div>
          )}
        </motion.div>
      </div>
    </AnimatePresence>
  );
}
