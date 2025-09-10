import React from 'react';
import './Header.css'; 
import { FaHeart, FaBolt, FaShieldAlt } from 'react-icons/fa'; 

function Header({ atributos }) {
    return (
        <div className='header-container'>
            <div className='progress-bar-container'>
                <FaHeart className='progress-icon' id='icon-one'/>
                <div className='progress-bar pixel'>
                    <div className='progress' style={{ width: atributos['CONTEUDO'] + '%' }}></div>
                </div>
            </div>
            <div className='progress-bar-container'>
                <FaBolt className='progress-icon' id='icon-two'/>
                <div className='progress-bar pixel'>
                    <div className='progress' style={{ width: atributos['CONDUTA'] + '%' }}></div>
                </div>
            </div>
            <div className='progress-bar-container'>
                <FaShieldAlt className='progress-icon' id='icon-three'/>
                <div className='progress-bar pixel'>
                    <div className='progress' style={{ width: atributos['CONTATO'] + '%' }}></div>
                </div>
            </div>
        </div>
    );
}

export default Header;
