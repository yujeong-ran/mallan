import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import media from '../../styles/breakPoint';
import VictoryResultList from '../../components/liar/VictoryResultList';
import VictoryResultVote from '../../components/liar/VictoryResultVote';

const VictoryContainer = styled.div`
  display: flex;
  gap: 20px;

  > div {
    flex: 1;
    padding: 50px 40px 40px;
    border-radius: ${({ theme }) => theme.borderRad};
    background: ${({ theme }) => theme.subBg};
  }

  ${media.medium`
      display:block;

      > div + div {margin-top:40px;}
  `}
`;

function LiarVictory() {
  const { winner } = useParams<{ winner?: string }>();

  return (
    <VictoryContainer>
      <div>
        <VictoryResultList winner={winner ?? 'liar'} />
      </div>
      <div>
        <VictoryResultVote />
      </div>
    </VictoryContainer>
  );
}

export default LiarVictory;
