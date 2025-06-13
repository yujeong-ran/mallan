import { api } from '../utils/api';

export const getRoomInfoApi = async (roomCode: string) => {
  try {
    const res = await api.get(`/liar/room/${roomCode}/ready`);

    console.log(res.data);
    return res.data;
  } catch (error) {
    console.error('생성 실패', error);
    throw error;
  }
};
