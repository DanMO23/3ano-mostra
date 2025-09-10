import React from 'react';
import { Link } from 'react-router-dom'
import './Home.Module.css'
import Btsettings from '../../assets/beginImg/IcEngrenagem.png'
import Button from '../../components/button/Button'

function Home() {
    return (
        <div className="home-container">
            <div className='title-home'>
                <h1>
                    Reinados da Web
                </h1>
            </div>
            
            <div className='container-buttons-home'>
                <Link to={{ pathname: "/registrar", state: { title: "Registro do Aluno" } }}>
                    <Button>PLAY</Button>
                </Link>
                <Link to={{ pathname: "/section", state: { title: "Seções" } }}>
                    <Button>PROFESSOR</Button>
                </Link>
            </div>
        </div>
    );
}

export default Home;