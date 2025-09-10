import React from 'react';
import ReactDOM from 'react-dom';
import './PopUp.css';

const PopUp = ({ 
  mensagem, 
  corMensagem = "#333", 
  onOk, 
  onCancel, 
  cancelButton, // Adiciona cancelButton como parâmetro opcional
  showButton = true 
}) => {
  const linhas = mensagem.split('\n');

  return (
    <>
      {ReactDOM.createPortal(
        <div className="overlay" />, 
        document.body 
      )}
      {ReactDOM.createPortal(
        <div className="popup-container">
          <div className="janela">
            <div className="janela-header">
              <div className="janela-icon verde"></div>
              <div className="janela-icon amarelo"></div>
              <div className="janela-icon vermelho"></div>
            </div>
            <div className="janela-corpo">
              {linhas.map((linha, index) => (
                <p key={index} className="mensagem" style={{ color: corMensagem }}>
                  {linha}
                </p>
              ))}
            </div>
            <div className="janela-footer">
              {showButton && (
                <>
                  <button className="botao-ok" onClick={onOk}>
                    OK
                  </button>
                  {cancelButton} {/* Renderiza o botão de cancelar se fornecido */}
                </>
              )}
            </div>
          </div>
        </div>,
        document.body 
      )}
    </>
  );
};

export default PopUp;