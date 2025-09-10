import React, { createContext, useContext, useState } from 'react';

const AlunoContext = createContext();

export const useAluno = () => useContext(AlunoContext);

export const AlunoProvider = ({ children }) => {
  const [aluno, setAluno] = useState(null);

  const login = (alunoData) => {
    setAluno(alunoData);
  };

  const logout = () => {
    setAluno(null);
  };

  return (
    <AlunoContext.Provider value={{ aluno, login, logout }}>
      {children}
    </AlunoContext.Provider>
  );
};
