import styled from 'styled-components';
import media from '../../styles/breakPoint';

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

function VictoryResultVote() {
  return (
    <>
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
            <Progress value={80} max={100} />
          </VoteProgress>
        </VoteProgressWrap>
      </ul>
    </>
  );
}

export default VictoryResultVote;
