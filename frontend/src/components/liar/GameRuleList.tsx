import media from '../../styles/breakPoint';
import styled from 'styled-components';

const RuleList = styled.ul`
  display: flex;
  max-width: 900px;
  width: 100%;
  margin: 100px auto 0;
  gap: 30px;
  flex-wrap: wrap;

  ${media.medium`
    margin:50px auto 0;
    gap: 15px;
  `}

  li {
    display: flex;
    align-items: center;
    justify-content: center;
    flex: 1;
    padding: 36px 20px;
    text-align: center;
    border-radius: ${({ theme }) => theme.borderRad};
    background: ${({ theme }) => theme.subBg};

    ${media.small`
      flex:auto;
      width:100%;
      padding:24px 10px;
  `}

    div p {
      font-size: 20px;
      ${media.medium`
        font-size:18px;
      `}
    }

    div span {
      display: block;
      margin-top: 14px;
      color: ${({ theme }) => theme.subPoint};
      line-height: 1.5;
      ${media.medium`
        font-size:15px;
      `}
    }
  }
`;

function GameRuleList() {
  return (
    <RuleList>
      <li>
        <div>
          <p>라이어를 찾아라</p>
          <span>
            누가 진실을 말하고 누가 거짓을
            <br /> 말하는지 추리하세요.
          </span>
        </div>
      </li>
      <li>
        <div>
          <p>친구들과 함께</p>
          <span>
            최대 10명까지 함께
            <br /> 플레이할 수 있습니다.
          </span>
        </div>
      </li>
      <li>
        <div>
          <p>투표로 결정</p>
          <span>
            누가 라이어인지
            <br />
            투표로 결정하세요.
          </span>
        </div>
      </li>
    </RuleList>
  );
}

export default GameRuleList;
