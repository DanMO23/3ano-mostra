import React from 'react';
import { Link } from 'react-router-dom';
import './Section.Module.css';
import '../../components/camp_field/CampField.css';
import '../management/Management.Module.css'
import IniciarSessao from '../../components/iniciar_sessao/InicarSessao';
import IniciarCadastro from '../../components/iniciar_cadastro/IniciarCadastro';
import BackgroundScreen from '../../components/background_screen/BackgroundScreen';
import BeckButton from '../../components/beck_button/BeckButton'
import Container from '../../components/container/Container';


function Section() {
  const [isCadastro, setIsCadastro] = React.useState(false);

  return (
    <body className="backgroundSection">
        <Container>
          <Link to="/">
            <BeckButton></BeckButton>
          </Link>
          
            {isCadastro ? (
              <BackgroundScreen title={"CRIAR CONTA"}>
                <IniciarCadastro />
              </BackgroundScreen>
            ) : (
              <BackgroundScreen title={"INICIAR SESSÃO"}>
                <IniciarSessao setIsCadastro={setIsCadastro} />
              </BackgroundScreen>
            )}
          </Container>
    </body>
  );
}

export default Section;
