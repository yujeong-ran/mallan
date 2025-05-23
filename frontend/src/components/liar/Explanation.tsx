import styled from 'styled-components';
import media from '../../styles/breakPoint';
import Timer from './Timer';

const ExplanationCon = styled.div`
  padding: 40px;
  margin-bottom: 10px;
  border-radius: ${({ theme }) => theme.borderRad};
  background: #f7f7f7;

  ${media.medium`
    padding:30px;
  `}
`;

const Speaker = styled.div`
  text-align: center;

  > p {
    color: ${({ theme }) => theme.textPoint};
    font-size: 16px;
    margin-bottom: 14px;
  }
  > strong {
    font-size: 24px;
  }
`;

const WordGuide = styled.div`
  padding: 12px 0;
  color: ${({ theme }) => theme.textBase};
  text-align: center;
  border-radius: ${({ theme }) => theme.borderRadSm};
  background-color: #fff;

  span {
    color: ${({ theme }) => theme.point};
  }
`;

const Button = styled.button`
  width: 100%;
  height: 50px;
  margin-top: 10px;
  color: #fff;
  font-size: 16px;
  border-radius: ${({ theme }) => theme.borderRadSm};
  background: ${({ theme }) => theme.point};
`;

function Explanation() {
  return (
    <ExplanationCon>
      <Speaker>
        <p>현재 발언자</p>
        <strong>귀여운 오리</strong>
      </Speaker>
      <Timer />
      <WordGuide>
        <span>호랑이</span>에 대해 설명하세요!
      </WordGuide>
      <Button>다음 사람에게 넘기기</Button>
    </ExplanationCon>
  );
}

export default Explanation;
