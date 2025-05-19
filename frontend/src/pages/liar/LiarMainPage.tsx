import { useParams } from 'react-router-dom';
import ContContainer from '../../components/liar/ContContainer';
import CharacterSet from '../../components/common/CharacterSet';
import GameRuleList from '../../components/liar/GameRuleList';

function LiarMainPage() {
  const { roomId } = useParams();
  const isGuest = !!roomId;

  return (
    <>
      <ContContainer>
        <CharacterSet isGuest={isGuest} />
      </ContContainer>
      <GameRuleList />
    </>
  );
}

export default LiarMainPage;
