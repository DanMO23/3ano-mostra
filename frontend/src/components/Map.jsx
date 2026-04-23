import React from 'react';
import { motion } from 'framer-motion';
import { useGameStore } from '../store/useGameStore';
import { MapPin, Lock } from 'lucide-react';
import './Map.css';

const ZONES = [
  { id: 'floresta-das-redes', name: 'Floresta das Redes', x: 20, y: 30, color: 'var(--color-tertiary)' },
  { id: 'montanha-gamer', name: 'Montanha Gamer', x: 50, y: 50, color: 'var(--color-accent)' },
  { id: 'vila-segura', name: 'Vila Segura', x: 80, y: 20, color: 'var(--color-primary)' }
];

export default function Map() {
  const { unlockedZones, openChallenge } = useGameStore();

  return (
    <div className="map-container">
      {/* Background visual elements can go here. We'll use simple CSS for the map background */}
      <div className="map-background">
        <motion.div 
          className="cloud cloud-1"
          animate={{ x: [0, 1000, 0] }}
          transition={{ duration: 40, repeat: Infinity, ease: "linear" }}
        />
        <motion.div 
          className="cloud cloud-2"
          animate={{ x: [1000, 0, 1000] }}
          transition={{ duration: 50, repeat: Infinity, ease: "linear" }}
        />
      </div>

      {ZONES.map((zone) => {
        const isUnlocked = unlockedZones.includes(zone.id);
        
        return (
          <motion.div
            key={zone.id}
            className={`map-pin ${isUnlocked ? 'unlocked' : 'locked'}`}
            style={{ left: `${zone.x}%`, top: `${zone.y}%` }}
            whileHover={{ scale: 1.2, y: -10 }}
            whileTap={{ scale: 0.9 }}
            onClick={() => isUnlocked ? openChallenge(zone.id) : null}
          >
            <div className="pin-icon" style={{ backgroundColor: isUnlocked ? zone.color : 'var(--color-text-light)' }}>
              {isUnlocked ? <MapPin size={32} color="#fff" /> : <Lock size={24} color="#fff" />}
            </div>
            <div className="pin-label glass-panel">
              {zone.name}
            </div>
          </motion.div>
        );
      })}
    </div>
  );
}
