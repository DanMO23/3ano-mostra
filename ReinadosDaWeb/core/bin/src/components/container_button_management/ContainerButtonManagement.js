import React from 'react';
import Button from '../../components/button/Button';
import './ContainerButtonManagement.css';

function ContainerButtonManagement({ handleVisibleScreen }) {
    return (
        <div className="formi-management">
            <div className="container-button-management" id="container-button-management">
                <Button onClick={() => handleVisibleScreen('1')}>Criar Turma</Button>
                <Button onClick={() => handleVisibleScreen('2')}>Turmas Cadastradas</Button>
                <Button onClick={() => handleVisibleScreen('3')}>Relatórios</Button>
            </div>
        </div>
    );
}

export default ContainerButtonManagement;