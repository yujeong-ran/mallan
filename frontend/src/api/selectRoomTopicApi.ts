import { api } from '../utils/api';

export const selectRoomTopicApi = async (
  roomCode: string,
  hostId: string,
  topicId: number,
) => {
  try {
    const res = await api.patch(`/liar/room/${roomCode}/topic`, {
      hostId,
      topicId,
    });

    console.log(res.data);
    return res.data;
  } catch (error) {
    console.error('실패');
  }
};
