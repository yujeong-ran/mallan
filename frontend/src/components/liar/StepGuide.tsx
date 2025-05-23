import styled from 'styled-components';

const GuideBox = styled.div`
  display: flex;
  align-items: flex-start;
  padding: 24px;
  margin-bottom: 16px;
  border-radius: ${({ theme }) => theme.borderRad};
  background: #f7f7f7;

  > span {
    padding: 6px 10px;
    margin-right: 20px;
    color: ${({ theme }) => theme.point};
    border-radius: 40px;
    border: 1px solid #d9d9d9;
    background-color: #f4f2f2;
  }

  > p {
    line-height: 1.5;
  }
`;

function StepGuide() {
  return (
    <GuideBox>
      <span>발언 단계</span>
      <p>
        모두 돌아가며 제시어에 대해 한마디씩 해주세요.
        <br />
        너무 티 나게 말하면 라이어가 눈치챌 수도 있어요!
      </p>
    </GuideBox>
  );
}

export default StepGuide;
