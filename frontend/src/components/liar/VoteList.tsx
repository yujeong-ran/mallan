import styled from 'styled-components';
import media from '../../styles/breakPoint';

const VoteCon = styled.div`
  display: flex;
  flex-direction: column;
  padding: 30px;
  border-radius: ${({ theme }) => theme.borderRad};
  min-height: 350px;
  background: #f7f7f7;

  ${media.medium`
    padding:30px;
  `}
`;

const VoteListStyle = styled.ul`
  display: flex;
  flex-wrap: wrap;
  gap: 4px;

  li {
    display: flex;
    position: relative;
    width: calc(50% - 2px);
    border: 1px solid ${({ theme }) => theme.border};
    border-radius: ${({ theme }) => theme.borderRadSm};
    align-items: center;
    background: #fff;
    color: ${({ theme }) => theme.textBase};
    font-size: 14px;
    box-sizing: border-box;

    ${media.small`
      width: 100%;
    `}

    > div {
      position: absolute;
      z-index: 1;
      top: 50%;
      left: 20px;
      width: 30px;
      height: 30px;
      flex-shrink: 0;
      margin-right: 14px;
      border: 1px solid ${({ theme }) => theme.border};
      border-radius: 100%;
      transform: translateY(-50%);
    }

    svg {
      margin-right: 4px;
    }
  }
`;

const Button = styled.button`
  width: 100%;
  height: 50px;
  margin-top: auto;
  color: #fff;
  font-size: 16px;
  border-radius: ${({ theme }) => theme.borderRadSm};
  background: ${({ theme }) => theme.point};
`;

const RadioInput = styled.label`
  position: relative;
  z-index: 2;
  display: block;
  width: 100%;
  height: 100%;
  padding: 15px 20px;
  padding-left: 65px;
  padding-right: 55px;
  line-height: 1.5;
  cursor: pointer;
  box-sizing: border-box;

  i {
    position: absolute;
    top: 0;
    left: 0;
    width: calc(100% - 1px);
    height: calc(100% - 1px);
    border: 1px solid transparent;
    border-radius: ${({ theme }) => theme.borderRadSm};
    opacity: 0;
    transition: all 0.2s ease;
  }
  input:checked + i {
    opacity: 1;
    border-color: ${({ theme }) => theme.point};
  }
  input:checked + i:after {
    content: '';
    position: absolute;
    top: 50%;
    right: 20px;
    width: 20px;
    height: 20px;
    opacity: 1;
    border-color: ${({ theme }) => theme.point};
    border-radius: 100%;
    background: ${({ theme }) => theme.point} url('/image/rad_chk_on.svg')
      no-repeat center;
    transform: translateY(-50%);
  }
  input:checked ~ span {
    color: ${({ theme }) => theme.point};
  }
`;

function VoteList() {
  return (
    <VoteCon>
      <VoteListStyle>
        <li>
          <div></div>
          <RadioInput>
            <input type="radio" name="liar-vote" id="" />
            <i></i>
            <span>닉네임 1</span>
          </RadioInput>
        </li>
        <li>
          <div></div>
          <RadioInput>
            <input type="radio" name="liar-vote" id="" />
            <i></i>
            <span>닉네임 2</span>
          </RadioInput>
        </li>
        <li>
          <div></div>
          <RadioInput>
            <input type="radio" name="liar-vote" id="" />
            <i></i>
            <span>닉네임 1</span>
          </RadioInput>
        </li>
        <li>
          <div></div>
          <RadioInput>
            <input type="radio" name="liar-vote" id="" />
            <i></i>
            <span>닉네임 2</span>
          </RadioInput>
        </li>
        <li>
          <div></div>
          <RadioInput>
            <input type="radio" name="liar-vote" id="" />
            <i></i>
            <span>닉네임 1</span>
          </RadioInput>
        </li>
        <li>
          <div></div>
          <RadioInput>
            <input type="radio" name="liar-vote" id="" />
            <i></i>
            <span>닉네임 2</span>
          </RadioInput>
        </li>
        <li>
          <div></div>
          <RadioInput>
            <input type="radio" name="liar-vote" id="" />
            <i></i>
            <span>닉네임 1</span>
          </RadioInput>
        </li>
        <li>
          <div></div>
          <RadioInput>
            <input type="radio" name="liar-vote" id="" />
            <i></i>
            <span>닉네임 2</span>
          </RadioInput>
        </li>
        <li>
          <div></div>
          <RadioInput>
            <input type="radio" name="liar-vote" id="" />
            <i></i>
            <span>닉네임 1</span>
          </RadioInput>
        </li>
        <li>
          <div></div>
          <RadioInput>
            <input type="radio" name="liar-vote" id="" />
            <i></i>
            <span>닉네임 2</span>
          </RadioInput>
        </li>
      </VoteListStyle>
      <Button>투표 완료하기</Button>
    </VoteCon>
  );
}

export default VoteList;
