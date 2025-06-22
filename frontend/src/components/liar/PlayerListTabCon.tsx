import { useEffect, useState } from 'react';
import styled, { __PRIVATE__ } from 'styled-components';
import media from '../../styles/breakPoint';
import { BiCopy } from 'react-icons/bi';
import { TiStarFullOutline } from 'react-icons/ti';
import { useNavigate, useParams } from 'react-router-dom';
import { getRoomInfoApi } from '../../api/getRoomInfoApi';
import { gameStartApi } from '../../api/gameStartApi';

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

const ContWrap = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const RoomCode = styled.div`
  display: flex;
  gap: 10px;
  align-items: center;
`;

const CodeCopy = styled.button`
  padding: 4px 8px;
  font-size: 16px;
  border-radius: 20px;
  border: 1px solid ${({ theme }) => theme.border};
  background: #fff;

  svg {
    margin-left: 4px;
    vertical-align: middle;
  }

  ${media.small`
    font-size:14px;
  `}
`;

const PlayerList = styled.ul`
  display: flex;
  flex-wrap: wrap;
  margin-top: 20px;
  gap: 4px;

  li {
    display: flex;
    width: calc(50% - 2px);
    border: 1px solid ${({ theme }) => theme.border};
    border-radius: ${({ theme }) => theme.borderRadSm};
    align-items: center;
    background: #fff;
    padding: 6px 16px;
    color: ${({ theme }) => theme.textBase};
    font-size: 14px;
    box-sizing: border-box;

    ${media.small`
      width: 100%;
    `}

    > div {
      width: 24px;
      height: 24px;
      flex-shrink: 0;
      margin-right: 10px;
      border: 1px solid ${({ theme }) => theme.border};
      border-radius: 100%;
      overflow: hidden;

      img {
        width: 100%;
      }
    }

    svg {
      margin-right: 4px;
    }
  }
`;

const Button = styled.button`
  width: 100%;
  height: 40px;
  margin-top: 20px;
  color: #fff;
  font-size: 16px;
  border-radius: ${({ theme }) => theme.borderRadSm};
  background: ${({ theme }) => theme.point};
`;

function PlayerListTabCon() {
  const { roomCode } = useParams();
  const navigate = useNavigate();
  const [roomInfo, setRoomInfo] = useState<RoomInfo | null>(null);
  const playerId = localStorage.getItem('playerId');

  useEffect(() => {
    if (!roomCode) return alert('방 코드가 없습니다.');

    const roomInfo = async () => {
      try {
        const data = await getRoomInfoApi(roomCode);
        console.log(data);

        setRoomInfo(data);
      } catch (error) {
        console.error('방 정보 불러오기 실패', error);
      }
    };

    roomInfo();
  }, [roomCode]);

  const isHost = roomInfo?.data?.players[0].playerId === playerId;

  const handleGameStart = () => {
    if (!isHost) {
      alert('방장이 시작할 때까지 기다려 주세요.');
      return;
    } else {
      gameStartApi(roomCode ?? '', playerId ?? '');
      navigate(`/liar/explanation/${roomCode}`);
    }
  };

  if (!roomInfo) {
    return <>방 정보를 가져오는 중 오류가 발생했습니다.</>;
  }

  return (
    <ContWrap>
      <RoomCode>
        <p>방 코드 :</p>
        <CodeCopy>
          {roomInfo?.data?.roomCode}
          <BiCopy />
        </CodeCopy>
      </RoomCode>
      <PlayerList>
        {roomInfo?.data?.players.map((player, i) => {
          return (
            <li key={player.playerId}>
              <div>
                <img src={player.profileImage} alt="" />
              </div>
              {i === 0 && <TiStarFullOutline />}
              {player.nickname}
            </li>
          );
        })}
      </PlayerList>
      <Button onClick={handleGameStart}>게임 시작하기</Button>
    </ContWrap>
  );
}

export default PlayerListTabCon;
