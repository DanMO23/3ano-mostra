import React from 'react';
import { useGameStore } from '../store/useGameStore';
import { motion } from 'framer-motion';
import { User, Backpack, Shield, Search, Star } from 'lucide-react';
import './HUD.css';

export default function HUD() {
  const { studentName, studentLevel, inventory } = useGameStore();

  const renderItemIcon = (itemType) => {
    switch(itemType) {
      case 'escudo': return <Shield size={24} color="var(--color-primary)" />;
      case 'lupa': return <Search size={24} color="var(--color-accent)" />;
      default: return <Star size={24} color="var(--color-secondary)" />;
    }
  };

  return (
    <div className="hud-container pointer-events-none">
      {/* Header Info */}
      <motion.div 
        className="glass-panel header-panel pointer-events-auto"
        initial={{ y: -50, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ type: 'spring', stiffness: 100 }}
      >
        <div className="avatar-circle">
          <User size={32} color="var(--color-white)" />
        </div>
        <div className="student-info">
          <h2>{studentName}</h2>
          <p>Nível {studentLevel}</p>
        </div>
      </motion.div>

      {/* Inventory Panel */}
      <motion.div 
        className="glass-panel inventory-panel pointer-events-auto"
        initial={{ x: 50, opacity: 0 }}
        animate={{ x: 0, opacity: 1 }}
        transition={{ type: 'spring', stiffness: 100, delay: 0.2 }}
      >
        <div className="inventory-header">
          <Backpack size={24} color="var(--color-text)" />
          <h3>Mochila</h3>
        </div>
        <div className="inventory-slots">
          {inventory.length === 0 ? (
            <p className="empty-text">Mochila vazia</p>
          ) : (
            inventory.map((item, index) => (
              <motion.div 
                key={index} 
                className="inventory-item"
                whileHover={{ scale: 1.1 }}
                title={item.name}
              >
                {renderItemIcon(item.type)}
              </motion.div>
            ))
          )}
        </div>
      </motion.div>
    </div>
  );
}
