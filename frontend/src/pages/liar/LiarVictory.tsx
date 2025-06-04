import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import media from '../../styles/breakPoint';

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

const VoteTitle = styled.h3`
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: 700;
`;

const VoteProgressWrap = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;

  ${({ theme }) =>
    media.medium`
      display: block;

      & + li {
        margin-top: 20px;
      }

      > div + div {
        margin-top: 20px;
        padding-bottom: 20px;
        border-bottom: 1px solid ${theme.border};
      }
  `}
`;

const VoteUserInfo = styled.div`
  display: flex;
`;

const VoteProgress = styled.div`
  display: flex;
  align-items: center;

  > p {
    margin-right: 10px;
  }
`;

const Progress = styled.progress`
  height: 12px;
  appearance: none;
  background-color: #e8e8e8;
  border-radius: 10px;
  overflow: hidden;

  &::-webkit-progress-bar {
    background-color: #e8e8e8;
    border-radius: 10px;
  }

  &::-webkit-progress-value {
    background-color: ${({ theme }) => theme.point};
    border-radius: 10px;
  }

  &::-moz-progress-bar {
    background-color: ${({ theme }) => theme.point};
    border-radius: 10px;
  }
`;

const User = styled.div`
  width: 40px;
  height: 40px;
  margin-right: 10px;
  border: 1px solid ${({ theme }) => theme.border};
  border-radius: 100%;
  background: #fff;
`;

const Info = styled.div`
  > p {
    margin-bottom: 6px;
    color: ${({ theme }) => theme.textBase};
  }
`;

const Tag = styled.span<{ variant?: 'primary' | 'secondary' }>`
  margin-right: 6px;
  border-radius: 20px;
  padding: 4px 8px;
  color: #fff;
  font-size: 12px;
  background: ${({ variant, theme }) =>
    variant === 'secondary' ? '#000' : theme.point};
`;

function LiarVictory() {
  const { winner } = useParams();
  const isLiarWin = winner === 'liar';

  return (
    <VictoryContainer>
      <div>
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
                '완벽한 추리로 라이어를 밝혀냈습니다. <br />
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
      </div>
      <div>
        <VoteTitle>투표 결과</VoteTitle>
        <ul>
          <VoteProgressWrap>
            <VoteUserInfo>
              <User />
              <Info>
                <p>귀여운 오리</p>
                <Tag>라이어</Tag>
                <Tag variant="secondary">최다득표</Tag>
              </Info>
            </VoteUserInfo>
            <VoteProgress>
              <p>3</p>
              <Progress value={0.8} />
            </VoteProgress>
          </VoteProgressWrap>
        </ul>
      </div>
    </VictoryContainer>
  );
}

export default LiarVictory;
