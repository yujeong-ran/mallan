import { api } from '../utils/api';

export const roomExitApi = async (roomCode: string, playerId: string) => {
  try {
    const res = await api.post(`/liar/room/${roomCode}/exit`, {
      playerId,
    });
    console.log('퇴장 요청 성공', res);
    return res;
  } catch (error) {
    console.error('퇴장 요청 실패');
  }
};
