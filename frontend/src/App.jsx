import React, { useEffect } from 'react';
import HUD from './components/HUD';
import Map from './components/Map';
import ChallengeModal from './components/ChallengeModal';
import { useGameStore } from './store/useGameStore';

function App() {
  const { currentChallenge, fetchGameData } = useGameStore();

  useEffect(() => {
    fetchGameData();
  }, [fetchGameData]);

  return (
    <div className="app-container">
      <Map />
      <HUD />
      {currentChallenge && <ChallengeModal />}
    </div>
  );
}

export default App;
