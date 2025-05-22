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
`;

function LiarRoomPage() {
  const [activeIndex, setActiveIndex] = useState(0);

  const tabContArr = [
    {
      tabTitle: (
        <li
          className={activeIndex === 0 ? 'active' : ''}
          onClick={() => tabClickHandler(0)}
        >
          <button>참여자 리스트</button>
        </li>
      ),
      tabCont: <PlayerListTabCon />,
    },
    {
      tabTitle: (
        <li
          className={activeIndex === 1 ? 'active' : ''}
          onClick={() => tabClickHandler(1)}
        >
          <button>방 설정</button>
        </li>
      ),
      tabCont: <GameSetCon />,
    },
  ];

  const tabClickHandler = (index: number) => {
    setActiveIndex(index);
  };

  return (
    <>
      <ContContainer>
        <Cont>
          <TabList>
            {tabContArr.map((el) => {
              return el.tabTitle;
            })}
          </TabList>
          <TabCon>{tabContArr[activeIndex].tabCont}</TabCon>
        </Cont>
      </ContContainer>
      <GameRuleList />
    </>
  );
}

export default LiarRoomPage;
