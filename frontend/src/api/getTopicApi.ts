import { api } from '../utils/api';

export const getTopicApi = async () => {
  try {
    const res = await api.get('/liar/topics');

    return res.data;
  } catch (error) {
    console.error('추출 실패', error);
  }
};
