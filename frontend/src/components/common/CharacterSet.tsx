import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import media from '../../styles/breakPoint';
import CharacterRegister from './CharacterRegister';
import NickNameRegister from './NickNameRegister';
import { createPlayerApi } from '../../api/createPlayerApi';
import { roomJoinApi } from '../../api/roomJoinApi';
import avatar1 from '../../assets/image/avatar1.png';

const ContTitile = styled.h2`
  margin-bottom: 20px;
  text-align: center;
  font-size: 24px;

  ${media.medium`
    font-size:20px;
  `}
`;

const Cont = styled.div`
  padding: 34px 40px;
  border-radius: ${({ theme }) => theme.borderRad};
  background: ${({ theme }) => theme.subBg};

  ${media.medium`
    padding: 34px 24px;
  `}
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

function CharacterSet({ isGuest }: { isGuest: boolean }) {
  const navigate = useNavigate();
  const { roomCode } = useParams();

  const [nickname, setNickname] = useState('');
  const [profileImg, setProfileImg] = useState(avatar1);

  const handleClick = async () => {
    if (!nickname) return alert('닉네임을 입력하세요');

    try {
      let playData;

      if (isGuest) {
        if (!roomCode) return alert('방 코드가 없습니다.');
        playData = await roomJoinApi(nickname, profileImg, roomCode);

        const playerId = playData.data.playerId;

        if (playerId) {
          localStorage.setItem('playerId', playerId);
        }
        navigate(`/liar/lobby/${roomCode}`);
      } else {
        playData = await createPlayerApi(nickname, profileImg);

        navigate(`/liar/lobby/${playData.data.roomCode}`);
      }
      console.log(playData);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <Cont>
      <ContTitile>캐릭터 생성</ContTitile>
      {/* 캐릭터 등록 */}
      <CharacterRegister
        profileImg={profileImg}
        setProfileImg={setProfileImg}
      />
      {/* 닉네임 등록 */}
      <NickNameRegister
        placeholder={
          isGuest
            ? '닉네임을 입력하세요'
            : '방장은 바로 당신! 멋진 닉네임과 함께 방을 열어보세요.'
        }
        nickname={nickname}
        setNickname={setNickname}
      />
      <Button onClick={handleClick}>
        {isGuest ? '게임 참가하기' : '방 만들기'}
      </Button>
    </Cont>
  );
}

export default CharacterSet;
