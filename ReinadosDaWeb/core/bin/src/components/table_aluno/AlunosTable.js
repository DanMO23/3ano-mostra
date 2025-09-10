import React from 'react';
import { IoSearchOutline } from "react-icons/io5";
import '../table_turmas/TableTurmas.css';
import IconWithLabel from './../icon_with_label/IconWithLabel';

function AlunosTable({ alunos = [], onRowClick, onDelete }) {
    console.log("Propriedade alunos recebida:", alunos);

    if (!alunos || alunos.length === 0) {
        return <p>Nenhum aluno disponível.</p>;
    }

    return (
        <div className='format-table' id='alunoTable'>
            <table className="turmasTable">
                <thead>
                    <tr>
                        <th><IconWithLabel icon={<IoSearchOutline />} label="Nome" /></th>
                        <th><IconWithLabel icon={<IoSearchOutline />} label="Ver mais" /></th>
                    </tr>
                </thead>
                <tbody>
                    {alunos.length > 0 ? (
                        alunos.map((aluno) => (
                            <tr key={aluno.id}>
                                <td>{aluno.nome}</td>
                                <td id="paragrafo" onClick={() => onRowClick(aluno)}>
                                    <div className="button-detail">Detalhes</div>
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="2">Nenhum aluno cadastrado.</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default AlunosTable;
