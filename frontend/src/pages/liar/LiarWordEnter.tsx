import styled from 'styled-components';
import media from '../../styles/breakPoint';
import ContContainer from '../../components/liar/ContContainer';
import { LiaRedhat } from 'react-icons/lia';

const EnterCon = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  padding: 40px;
  margin-top: 50px;
  border-radius: ${({ theme }) => theme.borderRad};
  background: #f7f7f7;

  ${media.medium`
    padding:30px;
  `}
`;

const StyledIcon = styled(LiaRedhat)`
  position: absolute;
  z-index: 2;
  top: -80px;
  left: 50%;
  transform: translateX(-50%);
  font-size: 250px;
  color: #000;

  ${media.medium`
  top: -70px;
    font-size: 200px;
  `}
`;

const Text = styled.p`
  padding-top: 130px;
  padding-bottom: 30px;
  color: ${({ theme }) => theme.textPoint};
  text-align: center;
  line-height: 1.5;

  ${media.medium`
     padding-top: 100px;
  `}
`;

const Button = styled.button`
  width: 100%;
  height: 50px;
  color: #fff;
  font-size: 16px;
  border-radius: ${({ theme }) => theme.borderRadSm};
  background: ${({ theme }) => theme.point};
`;

const Input = styled.input`
  width: 100%;
  height: 40px;
  padding: 0 14px;
  margin-bottom: 10px;
  text-align: center;
  border: 0;
  border-radius: ${({ theme }) => theme.borderRadSm};
  border: 1px solid ${({ theme }) => theme.border};
  box-sizing: border-box;

  &::placeholder {
    color: ${({ theme }) => theme.textPlaceholder};
  }
`;

function LiarWordEnter() {
  return (
    <ContContainer>
      <EnterCon>
        <StyledIcon />
        <Text>
          <b>제시어를 직접 추측해 입력해보세요.</b>
          <br />
          정답을 맞히면 라이어의 승리입니다!
        </Text>
        <Input type="text" placeholder="단어를 입력해주세요" />
        <Button type="submit">작성 완료</Button>
      </EnterCon>
    </ContContainer>
  );
}

export default LiarWordEnter;
