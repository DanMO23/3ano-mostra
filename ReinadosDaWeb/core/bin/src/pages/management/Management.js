import React from 'react';
import './Management.Module.css'
import bg from '../../assets/beginImg/Background - Tela.png'
import CaseManagement from './CaseManagement'
import '../section/Section.Module.css'
import { Link, useNavigate } from 'react-router-dom'
import BeckButton from '../../components/beck_button/BeckButton'
import PopUp from '../../components/popup/PopUp';

function Management() {
   const navigate = useNavigate();
   const [isPopUpVisible, setIsPopUpVisible] = React.useState(false);
   const [vosibleScreen, setVisibleScreen] = React.useState('0');

   const handleVisibleScreen = (Screen) => {
      if (vosibleScreen === '0') {
         setIsPopUpVisible(true);

      }
      setVisibleScreen(Screen);
   }

   return (
      <body className='managemente-container'>
         <main className='main'>

            <BeckButton onClick={() => { handleVisibleScreen('0') }}></BeckButton>

            <CaseManagement caseW={vosibleScreen} setVisibleScreen={setVisibleScreen} />
         </main>
         {isPopUpVisible && (
            <PopUp
               mensagem={'Você deseja mesmo sair?'}
               onOk={() => { navigate('/section'); setIsPopUpVisible(false) }}
               onCancel={() => { setIsPopUpVisible(false); }} // Apenas fecha o pop-up ao cancelar
               cancelButton={
                  <button
                     className="botao-ok"
                     onClick={() => setIsPopUpVisible(false)}
                     style={{ marginLeft: '10px' }} // Adicionando margin-left
                  >
                     Cancelar
                  </button>
               } // Passando o botão de cancelar
            />
         )}
      </body>
   )
}

export default Management;