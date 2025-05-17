import { Link } from 'react-router-dom';
import styled from 'styled-components';

const HeaderContainer = styled.header`
  padding-top: 70px;

  h1 {
    display: block;
    text-align: center;
    color: #0271e0;
    font-family: 'JejuDoldam';

    a {
      display: inline-block;
      font-size: 46px;

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
