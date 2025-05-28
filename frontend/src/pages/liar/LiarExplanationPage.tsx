import styled from 'styled-components';
import media from '../../styles/breakPoint';
import ContContainer from '../../components/liar/ContContainer';
import StepGuide from '../../components/liar/StepGuide';
import { AiOutlineInfoCircle } from 'react-icons/ai';
import Explanation from '../../components/liar/Explanation';

const InfoText = styled.div`
  display: flex;
  justify-content: space-between;
  color: #fff;
  font-size: 14px;

  ${media.medium`
    display: block;
    margin-top:20px;

    p {
      margin-top:10px;
    }
  `}

  p svg {
    margin-right: 6px;
    vertical-align: top;
  }
`;

function LiarExplanationPage() {
  return (
    <ContContainer>
      <StepGuide
        title="발언 단계"
        description={
          <>
            모두 돌아가며 제시어에 대해 한마디씩 해주세요.
            <br />
            너무 티 나게 말하면 라이어가 눈치챌 수도 있어요!
          </>
        }
      />
      <Explanation />
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
