import styled from 'styled-components';
import media from '../../../styles/breakPoint';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { roomExitApi } from '../../../api/roomExitApi';
import { getRoomInfoApi } from '../../../api/getRoomInfoApi';

const GameTitleWrap = styled.div`
  position: relative;
`;

const GameTitle = styled.h1`
  padding: 70px 0;
  color: #fff;
  font-size: 46px;
  font-family: 'JejuDoldam';
  text-align: center;
  text-shadow: 0px 0px 19px rgba(0, 0, 0, 0.35);

  ${media.medium`
    padding: 50px 0;
    font-size:36px;
  `}
`;

const ExitBtn = styled.button`
  position: absolute;
  top: 50%;
  left: 0;
  color: #fff;
  height: 50px;
  font-size: 22px;
  font-family: 'JejuDoldam';
  transform: translateY(-50%);

  ${media.small`
    top:0;
    transform:inherit;
    font-size:12px;
  `}
`;

function LiarGameTitle({ title }: { title: string }) {
  const location = useLocation();
  const navigate = useNavigate();
  const isLobbyPage = location.pathname.startsWith('/liar/lobby/');
  const { roomCode } = useParams();
  const playerId = localStorage.getItem('playerId');

  const handleExit = () => {
    if (!roomCode) return;

    // 퇴장 api 호출
    roomExitApi(roomCode ?? '', playerId ?? '');

    // 페이지 이동 (초기화)
    navigate('/liar/room/');

    // 방 정보 퇴장시 다시 반영하기
    getRoomInfoApi(roomCode ?? '');

    // localStorage 초기화
    localStorage.removeItem('playerId');
  };

  return (
    <GameTitleWrap>
      <GameTitle>{title}</GameTitle>
      {isLobbyPage && (
        <ExitBtn type="button" onClick={handleExit}>
          &lt; 방 나가기
        </ExitBtn>
      )}
    </GameTitleWrap>
  );
}

export default LiarGameTitle;
