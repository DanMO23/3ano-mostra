import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './pages/home/Home';
import Section from './pages/section/Section';
import Management from './pages/management/Management'
import Settings from './pages/settings/Settings'
import Jogar from './pages/jogar/Jogar'
import Registrar from './pages/registrar/Registrar'
import { ProfessorProvider } from './context/ProfessorContext';
import { AlunoProvider } from './context/AlunoContext';
import Relatorio from './pages/relatorio/Relatorio';

function App() {
  return (
    <ProfessorProvider>
      <AlunoProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/section" element={<Section />} />
            <Route path="/management" element={<Management />} />
            <Route path="/settings" element={<Settings />} />
            <Route path="/jogar" element={<Jogar />} />
            <Route path="/registrar" element={<Registrar />} />
            <Route path="/relatorios" element={<Relatorio />} />
          </Routes>
        </BrowserRouter>
      </AlunoProvider>
    </ProfessorProvider>
  );
}

export default App;