import React from 'react';
import { SiGoogleclassroom } from "react-icons/si";
import { IoSearchOutline } from "react-icons/io5";
import './TableTurmas.css';
import IconWithLabel from './../icon_with_label/IconWithLabel';

function TurmasTable({ turmas = [], onRowClick, page = 0 }) {
    console.log("Propriedade turmas recebida:", turmas);

    if (!turmas || turmas.length === 0) {
        return <p>Nenhuma turma disponível.</p>;
    }

    return (
        <div className="format-table">
            <table className="turmasTable">
                <thead>
                    <tr>
                        <th><IconWithLabel icon={<SiGoogleclassroom />} label="Turma" /></th>
                        <th><IconWithLabel icon={<IoSearchOutline />} label="Ver mais" /></th>
                    </tr>
                </thead>
                <tbody>
                    {turmas.length > 0 ? (
                        turmas.map((turma) => (
                            <tr key={page === 0 ? turma.id : turma.turma.id}>
                                <td>{page === 0 ? turma.nomeTurma : turma.turma.nomeTurma}</td>
                                <td id="paragrafo" onClick={() => onRowClick(page === 0 ? turma : turma)}>
                                    <div className="button-detail">Detalhes</div>
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="2">Nenhuma turma cadastrada.</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default TurmasTable;
