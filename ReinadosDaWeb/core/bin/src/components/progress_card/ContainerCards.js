import React, { useState } from 'react';
import { CircularProgressbar } from 'react-circular-progressbar';
import 'react-circular-progressbar/dist/styles.css';
import './ContainerCards.css'; 
import PopUp from './../popup/PopUp';

const ProgressCard = ({ percentage, label, color, mensagem }) => {
  const [hovered, setHovered] = useState(false);  // Estado para controlar o hover
  const [showPopup, setShowPopup] = useState(false);  // Estado para controlar a exibição do PopUp

  const radius = 65; 
  const strokeWidth = 18; 
  const circumference = 2 * Math.PI * radius;
  const strokeOffset = circumference - (percentage / 100) * circumference; 

  const handleMouseEnter = () => {
    setHovered(true);
    setShowPopup(true);
  };

  const handleMouseLeave = () => {
    setHovered(false);
    setShowPopup(false);
  };

  return (
    <div
      className="progress-card"
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
    >
      <div className="circle-container">
        <svg width="200" height="200">
          <circle
            className="circle-background"
            cx="100"
            cy="100"
            r={radius}
            stroke="#e6e6e6"
            strokeWidth={strokeWidth}
            fill="none"
          />
          <circle
            className="circle-progress"
            cx="100"
            cy="100"
            r={radius}
            stroke={color}
            strokeWidth={strokeWidth}
            fill="none"
            strokeDasharray={circumference}
            strokeDashoffset={strokeOffset}
            style={{ transition: 'stroke-dashoffset 1s ease' }}
          />
        </svg>
        <div className="percentage-text" style={{ color: color }}>{`${percentage}%`}</div>
      </div>
      <h3>{label}</h3>

      {/* Exibe o PopUp somente se showPopup for verdadeiro */}
      {showPopup && <PopUp mensagem={mensagem} corMensagem={color} onOk={() => setShowPopup(false)} showButton={false} />}
    </div>
  );
};

const ContainerCards = ({ score1, score2, score3 }) => {
  return (
    <div className="progress-container">
      <ProgressCard 
        percentage={score1} 
        label="Contato" 
        color="#0069AA" 
        mensagem="Este é o progresso de contato. A meta é atingir 100%." 
      />
      <ProgressCard 
        percentage={score2} 
        label="Conduta" 
        color="#C42430" 
        mensagem="Este é o progresso de conduta. A meta é atingir 100%." 
      />
      <ProgressCard 
        percentage={score3} 
        label="Conteúdo" 
        color="#858585" 
        mensagem="Este é o progresso de conteúdo. A meta é atingir 100%." 
      />
    </div>
  );
};

export default ContainerCards;