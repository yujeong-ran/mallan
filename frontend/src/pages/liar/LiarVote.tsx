import ContContainer from '../../components/liar/ContContainer';
import StepGuide from '../../components/liar/StepGuide';
import VoteList from '../../components/liar/VoteList';

function LiarVote() {
  return (
    <ContContainer>
      <StepGuide
        title="투표 단계"
        description={
          <>
            이제 투표 시간이에요! <br />
            누가 라이어인지 찬찬히 떠올려보고, 가장 수상한 사람에게 표를 주세요.
          </>
        }
      />
      <VoteList />
    </ContContainer>
  );
}

export default LiarVote;
