import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import { getTopicApi } from '../../api/getTopicApi';
import { selectRoomTopicApi } from '../../api/selectRoomTopicApi';
import { getRoomInfoApi } from '../../api/getRoomInfoApi';
import { selectRoomRoundApi } from '../../api/selectRoomRoundApi';

interface Player {
  playerId: string;
  nickname: string;
  profileImage: string;
}

interface RoomData {
  roomCode: string;
  players: Player[];
}
interface RoomInfo {
  data: RoomData;
}
interface TopicType {
  id: number;
  name: string;
}

const ContWrap = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const SetList = styled.ul`
  li label {
    display: block;
    margin-bottom: 10px;
  }
  li + li {
    margin-top: 30px;
  }
`;

const Select = styled.select`
  width: 100%;
  padding: 10px 14px;
  font-size: 16px;
  border-radius: ${({ theme }) => theme.borderRadSm};
  border: 1px solid ${({ theme }) => theme.border};
  background: #fff url('/image/select_arw.svg') no-repeat center right 10px;
`;

function GameSetCon() {
  const { roomCode } = useParams();
  const [topics, setTopics] = useState<TopicType[]>([]);
  const [roomInfo, setRoomInfo] = useState<RoomInfo | null>(null);
  const [selectedTopic, setSelectedTopic] = useState<number | null>(null);
  const [selectedRound, setSelectedRound] = useState<number | null>(null);

  useEffect(() => {
    const topics = async () => {
      const res = await getTopicApi();
      //console.log(res);

      setTopics(res);
    };
    topics();
  }, []);

  useEffect(() => {
    if (!roomCode) return alert('방 코드가 없습니다.');

    const roomInfo = async () => {
      try {
        const data = await getRoomInfoApi(roomCode);
        //console.log(data);

        setRoomInfo(data);
      } catch (error) {
        console.error('방 정보 불러오기 실패', error);
      }
    };

    roomInfo();
  }, [roomCode]);

  const isHost =
    roomInfo?.data?.players[0].playerId === localStorage.getItem('playerId');
  console.log(isHost);
  const handleChnage = (e: React.ChangeEvent<HTMLSelectElement>) => {
    if (!isHost) {
      alert('해당 설정은 방장 권한이 필요합니다.');
      return;
    }
    setSelectedTopic(Number(e.target.value));
  };

  useEffect(() => {
    if (
      selectedTopic == null ||
      !roomCode ||
      !roomInfo?.data?.players[0]?.playerId
    )
      return;

    selectRoomTopicApi(
      roomCode,
      roomInfo?.data?.players[0].playerId,
      selectedTopic,
    );
  }, [selectedTopic]);

  // 라운드 설정
  const handleRoundChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    if (!isHost) {
      alert('해당 설정은 방장 권한이 필요합니다.');
      return;
    }
    let value = Number(e.target.value);

    if (value == selectedRound) return;
    setSelectedRound(value);
  };

  useEffect(() => {
    if (
      selectedRound == null ||
      !roomCode ||
      !roomInfo?.data?.players[0]?.playerId
    )
      return;

    selectRoomRoundApi(
      roomCode,
      roomInfo?.data?.players[0].playerId,
      selectedRound,
    );
  }, [selectedRound]);

  return (
    <ContWrap>
      <SetList>
        <li>
          <label htmlFor="category">주제 선택</label>
          <Select
            name=""
            id="category"
            onChange={handleChnage}
            value={selectedTopic ?? ''}
          >
            <option value="">주제 선택</option>
            {topics.map((topic) => {
              return (
                <option key={topic.id} value={topic.id}>
                  {topic.name}
                </option>
              );
            })}
          </Select>
        </li>
        <li>
          <label htmlFor="round">설명 라운드</label>
          <Select
            name=""
            id="round"
            onChange={handleRoundChange}
            value={selectedRound ?? ''}
          >
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
          </Select>
        </li>
      </SetList>
    </ContWrap>
  );
}

export default GameSetCon;
