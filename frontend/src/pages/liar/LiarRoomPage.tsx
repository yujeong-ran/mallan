import { useState } from 'react';
import styled from 'styled-components';
import media from '../../styles/breakPoint';
import ContContainer from '../../components/liar/ContContainer';
import GameRuleList from '../../components/liar/GameRuleList';
import PlayerListTabCon from '../../components/liar/PlayerListTabCon';
import GameSetCon from '../../components/liar/GameSetCon';

const Cont = styled.div`
  min-height: 420px;
  border-radius: ${({ theme }) => theme.borderRad};
  background: ${({ theme }) => theme.subBg};
  overflow: hidden;
`;

const TabList = styled.ul`
  display: flex;

  li {
    flex: 1;
    text-align: center;
    background: ${({ theme }) => theme.point};
    cursor: pointer;

    button {
      width: 100%;
      padding: 15px 0;
      color: rgba(255, 255, 255, 0.5);
      font-size: 16px;
    }

    &.active {
      button {
        color: #fff;
      }
    }
  }
`;

const TabCon = styled.div`
  padding: 34px 40px;

  ${media.medium`
    padding: 34px 24px;
  `}

  > div {
    &.hidden {
      display: none;
    }
  }
`;

const tabBtns = ['참여자 리스트', '방 설정'];

function LiarRoomPage() {
  const [activeIndex, setActiveIndex] = useState(0);

  const tabClickHandler = (index: number) => {
    setActiveIndex(index);
  };

  return (
    <>
      <ContContainer>
        <Cont>
          <TabList>
            {tabBtns.map((btn, i) => {
              return (
                <li
                  key={i}
                  className={activeIndex === i ? 'active' : ''}
                  onClick={() => tabClickHandler(i)}
                >
                  <button type="button">{btn}</button>
                </li>
              );
            })}
          </TabList>
          <TabCon>
            <div className={activeIndex === 0 ? 'block' : 'hidden'}>
              <PlayerListTabCon />
            </div>
            <div className={activeIndex === 1 ? 'block' : 'hidden'}>
              <GameSetCon />
            </div>
          </TabCon>
        </Cont>
      </ContContainer>
      <GameRuleList />
    </>
  );
}

export default LiarRoomPage;
