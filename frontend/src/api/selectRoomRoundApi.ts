import { api } from '../utils/api';

export const selectRoomRoundApi = async (
  roomCode: string,
  playerId: string,
  round: number,
) => {
  try {
    const res = await api.patch(`/liar/room/${roomCode}/round`, {
      playerId,
      round,
    });
    //console.log('성공', res);
    return res;
  } catch (error) {
    console.error('실패');
  }
};
