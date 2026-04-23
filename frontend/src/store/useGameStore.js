import { create } from 'zustand';
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/mapa';

export const useGameStore = create((set, get) => ({
  studentId: 1, // Placeholder Aluno ID
  studentName: 'Aventureiro',
  studentLevel: 1,
  unlockedZones: [1], 
  inventory: [],
  currentChallenge: null,
  zonesData: [], // Store zones from backend

  fetchGameData: async () => {
    const { studentId } = get();
    try {
      const zonesRes = await axios.get(`${API_BASE_URL}/zonas`);
      const progressRes = await axios.get(`${API_BASE_URL}/progresso/${studentId}`);
      
      const unlocked = progressRes.data.zonasCompletadas.map(p => p.arco.id);
      // Ensure initial zone is always present if none are completed
      const initialZone = 'floresta-das-redes';
      
      set({ 
        zonesData: zonesRes.data,
        unlockedZones: unlocked.length > 0 ? unlocked : [initialZone],
        inventory: progressRes.data.inventario.map(i => ({
          id: i.item.id,
          name: i.item.nome,
          type: i.item.tipoItem.toLowerCase(),
        })),
        studentLevel: unlocked.length + 1
      });
    } catch (error) {
      console.error("Error fetching game data:", error);
    }
  },

  unlockZone: async (arcoId) => {
    const { studentId, unlockedZones } = get();
    try {
      await axios.post(`${API_BASE_URL}/progresso/${studentId}/zona/${arcoId}/concluir`);
      set((state) => ({
        unlockedZones: state.unlockedZones.includes(arcoId) 
          ? state.unlockedZones 
          : [...state.unlockedZones, arcoId],
        studentLevel: state.studentLevel + 1
      }));
    } catch (error) {
      console.error("Error unlocking zone:", error);
    }
  },

  addItemToInventory: async (item) => {
    const { studentId } = get();
    try {
      if (item.id) {
        await axios.post(`${API_BASE_URL}/progresso/${studentId}/item/${item.id}/adicionar`);
      }
      set((state) => ({
        inventory: [...state.inventory, item]
      }));
    } catch (error) {
      console.error("Error adding item to inventory:", error);
    }
  },

  openChallenge: (zoneId) => set({ currentChallenge: zoneId }),
  closeChallenge: () => set({ currentChallenge: null })
}));
