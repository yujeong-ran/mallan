import { Link } from 'react-router-dom';
import styled from 'styled-components';
import media from '../styles/breakPoint';

const HeaderContainer = styled.header`
  padding-top: 70px;
  ${media.medium`
      padding-top:50px;
  `}

  h1 {
    display: block;
    text-align: center;
    color: #0271e0;
    font-family: 'JejuDoldam';

    a {
      display: inline-block;
      font-size: 46px;

      ${media.medium`
          font-size:36px;
      `}

      em {
        color: #ffd620;
      }
    }
  }
`;

function Header() {
  return (
    <HeaderContainer>
      <h1>
        <Link to="/">
          <em>y</em>u<em>j</em>eongran game
        </Link>
      </h1>
    </HeaderContainer>
  );
}

export default Header;
