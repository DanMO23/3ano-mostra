import React from 'react';
import './Carta.css';

function GameCard({
    position,
    rotation,
    onMouseDown,
    onMouseMove,
    onMouseUp,
    url
}) {
    return (
        <div
            className="game-card"
            style={{
                backgroundImage: `url(${url})`,
                left: position.x,
                top: position.y,
                transform: `rotate(${rotation}deg)`,
            }}
            onMouseDown={onMouseDown}
            onMouseUp={onMouseUp}
            onMouseMove={onMouseMove} 
        ></div>
    );
}

export default GameCard;