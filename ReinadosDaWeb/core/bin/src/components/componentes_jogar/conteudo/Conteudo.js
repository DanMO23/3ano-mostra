import React, { useState, useRef, useEffect } from 'react';
import './Conteudo.css';
import { Link } from 'react-router-dom';
import Carta from '../carta/Carta';

function Conteudo({ gameData, handleAtributo, handleArco }) {
    const [position, setPosition] = useState({ x: 0, y: 0 });
    const [rotation, setRotation] = useState(0);
    const [isDragging, setIsDragging] = useState(false);
    const [conjuntoCartas, setConjuntoCartas] = useState([]);
    const [cartaAtual, setCartaAtual] = useState(0);
    const positionRef = useRef(position);
    const isAnimatingRef = useRef(false);
    const hasChosenRef = useRef(false); // Flag to prevent double counting

    useEffect(() => {
        setConjuntoCartas(gameData.conjuntoCartas || []);
    }, [gameData]);

    useEffect(() => {
        const container = document.querySelector('.conteudo-container');
        
        const centerCard = () => {
            const cardWidth = window.innerWidth < 670 ? 200 : 270;
            const containerWidth = container.offsetWidth;
            const containerHeight = container.offsetHeight;
            const cardHeight = window.innerWidth < 670 ? 270 : 360;
            const initialX = (containerWidth - cardWidth) / 2;
            const initialY = (containerHeight - cardHeight) / 2;
    
            setPosition({ x: initialX, y: initialY });
            
            positionRef.current = { x: initialX, y: initialY };
        };
    
        centerCard(); 
        window.addEventListener('resize', centerCard);
    
        return () => {
            window.removeEventListener('resize', centerCard);
        };
    }, []);

    // Handle Mouse Events
    const handleMouseDown = (e) => {
        if (isAnimatingRef.current) return;
        setIsDragging(true);
        hasChosenRef.current = false; // Reset the flag when dragging starts
    };

    const handleMouseMove = (e) => {
        if (!isDragging || isAnimatingRef.current) return;
    
        const container = document.querySelector('.conteudo-container');
        const cardWidth = window.innerWidth < 670 ? 150 : 270;
        const containerWidth = container.offsetWidth;
    
        const buffer = 20;
        const newX = Math.min(
            Math.max(e.clientX - cardWidth / 2, buffer),
            containerWidth - cardWidth - buffer
        );

        const overlayLeft = document.querySelector('.overlay-left');
        const overlayRight = document.querySelector('.overlay-right');
    
        if (newX <= 100) {
            overlayLeft.classList.add('active');
            overlayRight.classList.remove('active');
        } else if (newX >= containerWidth - cardWidth - 100) {
            overlayRight.classList.add('active');
            overlayLeft.classList.remove('active');
        } else {
            overlayLeft.classList.remove('active');
            overlayRight.classList.remove('active');
        }
    
        const newRotation = newX <= 100 ? -10 : newX >= containerWidth - cardWidth - 100 ? 10 : 0;

        setPosition({ x: newX, y: position.y });
        setRotation(newRotation);
    };

    const handleMouseUp = () => {
        setIsDragging(false);
        const container = document.querySelector('.conteudo-container');
        const cardWidth = window.innerWidth < 670 ? 150 : 270;
        const containerWidth = container.offsetWidth;

        const overlayLeft = document.querySelector('.overlay-left');
        const overlayRight = document.querySelector('.overlay-right');

        overlayLeft.classList.remove('active');
        overlayRight.classList.remove('active');

        const initialX = (containerWidth - cardWidth) / 2;
        isAnimatingRef.current = true;

        const animateBackToCenter = setInterval(() => {
            setPosition((prevPos) => {
                let newX = prevPos.x;
                if (newX < initialX) newX = Math.min(newX + 10, initialX);
                else if (newX > initialX) newX = Math.max(newX - 10, initialX);

                if (newX === initialX) {
                    clearInterval(animateBackToCenter);
                    isAnimatingRef.current = false;
                }
                return { x: newX, y: prevPos.y };
            });
        }, 10);

        setRotation(0);

        if (position.x <= 100) {
            handleEscolhaCarta('ESQUERDA');
        } else if (position.x >= containerWidth - cardWidth - 100) {
            handleEscolhaCarta('DIREITA');
        }
    };

    // Handle Touch Events for Mobile
    const handleTouchStart = (e) => {
        if (isAnimatingRef.current) return;
        setIsDragging(true);
        hasChosenRef.current = false; // Reset the flag when dragging starts
    };

    const handleTouchMove = (e) => {
        if (!isDragging || isAnimatingRef.current) return;
    
        const container = document.querySelector('.conteudo-container');
        const cardWidth = window.innerWidth < 670 ? 150 : 270;
        const containerWidth = container.offsetWidth;
    
        const buffer = 20;
        const touchX = e.touches[0].clientX; // Get the touch X position
        const newX = Math.min(
            Math.max(touchX - cardWidth / 2, buffer),
            containerWidth - cardWidth - buffer
        );

        const overlayLeft = document.querySelector('.overlay-left');
        const overlayRight = document.querySelector('.overlay-right');
    
        if (newX <= 100) {
            overlayLeft.classList.add('active');
            overlayRight.classList.remove('active');
        } else if (newX >= containerWidth - cardWidth - 100) {
            overlayRight.classList.add('active');
            overlayLeft.classList.remove('active');
        } else {
            overlayLeft.classList.remove('active');
            overlayRight.classList.remove('active');
        }
    
        const newRotation = newX <= 100 ? -10 : newX >= containerWidth - cardWidth - 100 ? 10 : 0;

        setPosition({ x: newX, y: position.y });
        setRotation(newRotation);
    };

    const handleTouchEnd = () => {
        setIsDragging(false);
        const container = document.querySelector('.conteudo-container');
        const cardWidth = window.innerWidth < 670 ? 150 : 270;
        const containerWidth = container.offsetWidth;

        const overlayLeft = document.querySelector('.overlay-left');
        const overlayRight = document.querySelector('.overlay-right');

        overlayLeft.classList.remove('active');
        overlayRight.classList.remove('active');

        const initialX = (containerWidth - cardWidth) / 2;
        isAnimatingRef.current = true;

        const animateBackToCenter = setInterval(() => {
            setPosition((prevPos) => {
                let newX = prevPos.x;
                if (newX < initialX) newX = Math.min(newX + 10, initialX);
                else if (newX > initialX) newX = Math.max(newX - 10, initialX);

                if (newX === initialX) {
                    clearInterval(animateBackToCenter);
                    isAnimatingRef.current = false;
                }
                return { x: newX, y: prevPos.y };
            });
        }, 10);

        setRotation(0);

        if (position.x <= 100) {
            handleEscolhaCarta('ESQUERDA');
        } else if (position.x >= containerWidth - cardWidth - 100) {
            handleEscolhaCarta('DIREITA');
        }
    };

    const handleEscolhaCarta = (ladoEscolha) => {
        if (hasChosenRef.current) return; // Prevent double counting

        hasChosenRef.current = true; // Set the flag to true
        const resposta = ladoEscolha === conjuntoCartas[cartaAtual]?.ladoCorreto;
        console.log("Resposta: ", resposta)

        handleAtributo(
            conjuntoCartas[cartaAtual]?.tipoCarta,
            conjuntoCartas[cartaAtual]?.nivelCarta,
            resposta,
            conjuntoCartas[cartaAtual]?.id,
            ladoEscolha
        );

        if(ladoEscolha === 'ESQUERDA' && conjuntoCartas[cartaAtual]?.idCartaEsq !== -1) {
            const nextCard = conjuntoCartas[cartaAtual]?.idCartaEsq;
            setCartaAtual(nextCard - 1);
        } else if(ladoEscolha === 'DIREITA' && conjuntoCartas[cartaAtual]?.idCartaDire !== -1) {
            const nextCard = conjuntoCartas[cartaAtual]?.idCartaDire;
            setCartaAtual(nextCard - 1);
        } else {
            console.log("Fim do arco");
            handleArco();
            setCartaAtual(0);
        }
        console.log("conjuntoCartas ", conjuntoCartas)
    };

    return (
        <div
            className="conteudo-container"
            onMouseMove={handleMouseMove}
            onMouseUp={handleMouseUp}
            onTouchStart={handleTouchStart} // For touch start
            onTouchMove={handleTouchMove} // For touch move
            onTouchEnd={handleTouchEnd}   // For touch end
            style={{
                backgroundImage: `url(${gameData.backgroundArco})`,
            }}
        >
            <Link to="/">
                <button className="back-button"></button>
            </Link>

            <div className="overlay-left">
                <span>{conjuntoCartas[cartaAtual]?.mensagemEsquerda}</span>
            </div>

            <div className="overlay-right">
                <span>{conjuntoCartas[cartaAtual]?.mensagemDireita}</span>
            </div>

            <Carta
                position={position}
                rotation={rotation}
                onMouseDown={handleMouseDown}
                onMouseUp={handleMouseUp}
                url={conjuntoCartas[cartaAtual]?.urlImagem}
            />
        </div>
    );
}

export default Conteudo;
