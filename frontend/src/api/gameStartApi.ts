import { api } from '../utils/api';

export const gameStartApi = async (roomCode: string, playerId: string) => {
  try {
    const res = await api.patch(`/liar/room/${roomCode}/start`, {
      playerId,
    });
    console.log('요청 성공', res);
  } catch (error) {
    console.log('요청 실패', error);
  }
};
