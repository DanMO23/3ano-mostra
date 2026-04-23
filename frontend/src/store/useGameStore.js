import { create } from 'zustand';

export const useGameStore = create((set) => ({
  studentName: 'Aventureiro',
  studentLevel: 1,
  unlockedZones: ['floresta-das-redes'], // Initial zone unlocked
  inventory: [],
  currentChallenge: null, // null means no modal is open

  unlockZone: (zoneId) => set((state) => ({
    unlockedZones: state.unlockedZones.includes(zoneId) 
      ? state.unlockedZones 
      : [...state.unlockedZones, zoneId],
    studentLevel: state.studentLevel + 1
  })),

  addItemToInventory: (item) => set((state) => ({
    inventory: [...state.inventory, item]
  })),

  openChallenge: (zoneId) => set({ currentChallenge: zoneId }),
  closeChallenge: () => set({ currentChallenge: null })
}));
