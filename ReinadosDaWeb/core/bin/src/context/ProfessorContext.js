import React, { createContext, useContext, useState } from 'react';

const ProfessorContext = createContext();

export const useProfessor = () => useContext(ProfessorContext);

export const ProfessorProvider = ({ children }) => {
  const [professor, setProfessor] = useState(null);

  const login = (professorData) => {
    setProfessor(professorData);
  };

  const logout = () => {
    setProfessor(null);
  };

  return (
    <ProfessorContext.Provider value={{ professor, login, logout }}>
      {children}
    </ProfessorContext.Provider>
  );
};
