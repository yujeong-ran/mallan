import styled from 'styled-components';
import media from '../../styles/breakPoint';
import { BiCopy } from 'react-icons/bi';
import { TiStarFullOutline } from 'react-icons/ti';

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
  return (
    <ContWrap>
      <RoomCode>
        <p>방 코드 :</p>
        <CodeCopy>
          a1b2c3d4
          <BiCopy />
        </CodeCopy>
      </RoomCode>
      <PlayerList>
        <li>
          <div></div>
          <TiStarFullOutline />
          닉네임 1
        </li>
        <li>
          <div></div>닉네임 2
        </li>
        <li>
          <div></div>닉네임 3
        </li>
        <li>
          <div></div>닉네임 4
        </li>
      </PlayerList>
      <Button>게임 시작하기</Button>
    </ContWrap>
  );
}

export default PlayerListTabCon;
