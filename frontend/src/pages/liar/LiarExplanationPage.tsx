import styled from 'styled-components';
import ContContainer from '../../components/liar/ContContainer';
import StepGuide from '../../components/liar/StepGuide';
import { AiOutlineInfoCircle } from 'react-icons/ai';

const InfoText = styled.div`
  display: flex;
  justify-content: space-between;
  color: #fff;
  font-size: 14px;

  p svg {
    margin-right: 6px;
    vertical-align: top;
  }
`;

function LiarExplanationPage() {
  return (
    <ContContainer>
      <StepGuide />
      <InfoText>
        <p>
          <AiOutlineInfoCircle />
          10초 후에는 자동으로 다음 사람에게 넘어갑니다.
        </p>
        <p>플레이어 1/10</p>
      </InfoText>
    </ContContainer>
  );
}

export default LiarExplanationPage;
