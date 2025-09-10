import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Registrar.Module.css';
import BackgroundScreen from '../../components/background_screen/BackgroundScreen';
import BeckButton from '../../components/beck_button/BeckButton';
import IniciarRegistro from '../../components/iniciar_registro/IniciarRegistro'; // Importando o novo componente

const Registrar = () => {
    return (
        <div className="backgroundSection">
            <main className="main">
                <Link to="/">
                    <BeckButton />
                </Link>
                <div className='section-container'>
                    <BackgroundScreen title={"REGISTRO DO ALUNO"}>
                        <IniciarRegistro />
                    </BackgroundScreen>
                </div>
            </main>
        </div>
    );
}

export default Registrar;