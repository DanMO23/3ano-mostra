import React from 'react';
import { motion } from 'framer-motion';
import { useGameStore } from '../store/useGameStore';
import { MapPin, Lock } from 'lucide-react';
import './Map.css';

const FALLBACK_ZONES = [
  { id: 1, name: 'Floresta das Redes', posX: 20, posY: 30, color: 'var(--color-tertiary)' },
  { id: 2, name: 'Montanha Gamer', posX: 50, posY: 50, color: 'var(--color-accent)' },
  { id: 3, name: 'Vila Segura', posX: 80, posY: 20, color: 'var(--color-primary)' }
];

const COLORS = ['var(--color-tertiary)', 'var(--color-accent)', 'var(--color-primary)'];

export default function Map() {
  const { unlockedZones, openChallenge, zonesData } = useGameStore();

  const displayZones = zonesData.length > 0 ? zonesData : FALLBACK_ZONES;

  return (
    <div className="map-container">
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

      {displayZones.map((zone, index) => {
        const isUnlocked = unlockedZones.includes(zone.id);
        const zoneColor = COLORS[index % COLORS.length];
        
        return (
          <motion.div
            key={zone.id}
            className={`map-pin ${isUnlocked ? 'unlocked' : 'locked'}`}
            style={{ left: `${zone.posX || (index * 30 + 10)}%`, top: `${zone.posY || 40}%` }}
            whileHover={{ scale: 1.2, y: -10 }}
            whileTap={{ scale: 0.9 }}
            onClick={() => isUnlocked ? openChallenge(zone.id) : null}
          >
            <div className="pin-icon" style={{ backgroundColor: isUnlocked ? zoneColor : 'var(--color-text-light)' }}>
              {isUnlocked ? <MapPin size={32} color="#fff" /> : <Lock size={24} color="#fff" />}
            </div>
            <div className="pin-label glass-panel">
              {zone.nome || zone.name}
            </div>
          </motion.div>
        );
      })}
    </div>
  );
}
