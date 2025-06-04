import styled from 'styled-components';
import media from '../../styles/breakPoint';

type VictoryResultListProps = {
  winner: string;
};

const VictoryText = styled.div`
  display: flex;
  margin-bottom: 32px;
  justify-content: space-between;
  align-items: center;
  line-height: 1.4;

  ${media.small`
     display:block;
  `}

  > strong {
    font-size: 32px;
  }

  > p {
    color: ${({ theme }) => theme.textPoint};
    text-align: right;

    ${media.small`
      margin-top:10px;
      text-align:left;
    `}
  }
`;

const ResultList = styled.ul`
  display: flex;
  flex-wrap: wrap;

  li {
    position: relative;
    min-height: 140px;
    width: calc(25% - 10px);
    margin: 0 5px 10px;
    padding: 20px;
    text-align: center;
    border: 1px solid ${({ theme }) => theme.border};
    border-radius: ${({ theme }) => theme.borderRadSm};
    box-sizing: border-box;
    background: #fff;

    ${media.medium`
       width: calc(50% - 10px);
    `}

    ${media.small`
       width: 100%;
       margin:5px 0;
    `}

    > p {
      color: #8a8a8a;
      font-size: 15px;
      margin-bottom: 30px;
    }

    > strong {
      position: absolute;
      top: calc(50% + 10px);
      left: 50%;
      transform: translate(-50%, -50%);
    }

    &:nth-last-child(-n + 2) {
      width: calc(50% - 10px);
      min-height: 200px;

      ${media.small`
       width: 100%;
       margin:5px 0;
    `}
    }
  }
`;

const BtnWrap = styled.div`
  display: flex;
  gap: 10px;
  margin-top: 20px;
`;

const Button = styled.button<{ variant?: 'primary' | 'secondary' }>`
  flex: 1;
  height: 50px;
  color: ${({ variant, theme }) =>
    variant === 'secondary' ? '#fff' : theme.point};
  font-size: 16px;
  border-radius: ${({ theme }) => theme.borderRadSm};
  background: ${({ variant, theme }) =>
    variant === 'secondary' ? theme.point : theme.border};
`;

function VictoryResultList({ winner }: VictoryResultListProps) {
  const isLiarWin = winner === 'liar';

  return (
    <>
      <VictoryText>
        <strong>{isLiarWin ? '라이어 승!' : '플레이어 승!'}</strong>
        <p>
          {isLiarWin ? (
            <>
              라이어의 완벽한 속임수에 모두 속았어요! <br /> 어떤 결과가
              나왔는지 확인해볼까요?'
            </>
          ) : (
            <>
              완벽한 추리로 라이어를 밝혀냈습니다. <br />
              어떤 결과가 나왔는지 확인해볼까요?'
            </>
          )}
        </p>
      </VictoryText>
      <ResultList>
        <li>
          <p>주제</p>
          <strong>동물</strong>
        </li>
        <li>
          <p>제시어</p>
          <strong>호랑이</strong>
        </li>
        <li>
          <p>라이어</p>
          <strong>귀여운 오리</strong>
        </li>
        <li>
          <p>라이어 추측</p>
          <strong>호랑이</strong>
        </li>
        <li>
          <p>눈치 백단은 누구?</p>
        </li>
        <li>
          <p>라이어 편(?)</p>
        </li>
      </ResultList>
      <BtnWrap>
        <Button type="button" variant="secondary">
          게임 다시하기
        </Button>
        <Button type="button">대기방으로 가기</Button>
      </BtnWrap>
    </>
  );
}

export default VictoryResultList;
