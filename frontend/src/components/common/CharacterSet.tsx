import styled from 'styled-components';
import media from '../../styles/breakPoint';
import CharacterRegister from './CharacterRegister';
import NickNameRegister from './NickNameRegister';

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
  return (
    <Cont>
      <ContTitile>캐릭터 생성</ContTitile>
      {/* 캐릭터 등록 */}
      <CharacterRegister />
      {/* 닉네임 등록 */}
      <NickNameRegister
        placeholder={
          isGuest
            ? '닉네임을 입력하세요'
            : '방장은 바로 당신! 멋진 닉네임과 함께 방을 열어보세요.'
        }
      />

      <Button>{isGuest ? '게임 참가하기' : '방 만들기'}</Button>
    </Cont>
  );
}

export default CharacterSet;
