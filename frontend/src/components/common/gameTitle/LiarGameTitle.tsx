import styled from 'styled-components';
import media from '../../../styles/breakPoint';

const GameTitle = styled.h1`
  padding: 70px 0;
  color: #fff;
  font-size: 46px;
  font-family: 'JejuDoldam';
  text-align: center;
  text-shadow: 0px 0px 19px rgba(0, 0, 0, 0.35);

  ${media.medium`
    padding: 50px 0;
    font-size:36px;
  `}
`;

function LiarGameTitle({ title }: { title: string }) {
  return <GameTitle>{title}</GameTitle>;
}

export default LiarGameTitle;
