import { api } from '../utils/api';

export const roomJoinApi = async (
  nickname: string,
  profileImage: string,
  roomCode: string,
) => {
  try {
    const res = await api.post(`/liar/room/${roomCode}/join`, {
      nickname,
      profileImage,
    });

    console.log(res.data);

    return res.data;
  } catch (error) {
    console.error('생성 실패', error);
    throw error;
  }
};
