import { api } from '../utils/api';

export const createPlayerApi = async (
  nickname: string,
  profileImage: string,
) => {
  try {
    const res = await api.post('/liar/room', {
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
