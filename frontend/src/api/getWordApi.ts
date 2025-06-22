import { api } from '../utils/api';

export const getWordApi = async (roomCode: string, playerId: string) => {
  try {
    const res = await api.post(`/liar/room/${roomCode}/word`, {
      playerId,
    });
    console.log('요청 성공', res.data);
    return res.data;
  } catch (error) {
    console.log('요청 실패', error);
  }
};
