import React from 'react';
import { Link, useNavigate } from 'react-router-dom'
import './Management.Module.css'
import ContainerButtonManagement from '../../components/container_button_management/ContainerButtonManagement';
import TableTurmas from '../../components/table_turmas/TableTurmas';
import BackgroundScreen from '../../components/background_screen/BackgroundScreen';
import IniciarCadastroTurma from '../../components/iniciar_cadastro_turma/IniciarCadastroTurma';
import Relatorio from '../relatorio/Relatorio';

function CaseManagement({ caseW, setVisibleScreen}) {
    const [nameBackground, setNameBackground] = React.useState('Turmas Cadastradas');

    const handleNameBackground = (name) => {
        setNameBackground(name);
    }

    switch (caseW) {
        case '0':
            return(
                <BackgroundScreen title={"Gerenciamento da Turma"}> 
                    <ContainerButtonManagement handleVisibleScreen={setVisibleScreen} />
                </BackgroundScreen>      
            )
        case '1':
            return(        
                <BackgroundScreen title={"Cadastrar Turma"}>
                    <IniciarCadastroTurma />
                </BackgroundScreen>    
            )
        case '2':
            return(
                <BackgroundScreen title={nameBackground}>
                    <TableTurmas handleNameBackground={handleNameBackground}/>
                </BackgroundScreen>   
            )
        case '3':
            return(
                <BackgroundScreen title={"RELATÓRIO TURMA"}>
                    <Relatorio />
                </BackgroundScreen>
            )
    }
}
export default CaseManagement