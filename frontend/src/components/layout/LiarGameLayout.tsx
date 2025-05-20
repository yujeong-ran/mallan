import { Outlet } from 'react-router-dom';
import styled, { ThemeProvider } from 'styled-components';
import media from '../../styles/breakPoint';
import { gameThemes } from '../../styles/gameThemes';
import LiarGameTitle from '../common/gameTitle/LiarGameTitle';
import Footer from '../Footer';

const Wrap = styled.div`
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  padding: 0 36px;
  background-color: ${({ theme }) => theme.subPoint};
  overflow: hidden;

  ${media.medium`
      padding: 0 24px;
  `}

  &:before {
    content: '';
    position: absolute;
    z-index: -1;
    top: 50%;
    left: 50%;
    width: 1032px;
    height: 988px;
    background: url('/image/gun.svg') no-repeat center;
    background-size: 100%;
    transform: translate(-50%, -50%);

    ${media.medium`
      width: 400px;
      height: 400px;
      top:-100px;
      left:-100px;
      transform: inherit;
  `}
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  color: ${({ theme }) => theme.point};
`;

function LiarGameLayout() {
  return (
    <ThemeProvider theme={gameThemes.liar}>
      <Wrap>
        <LiarGameTitle title={'라이어 게임'} />
        <Container>
          <Outlet />
        </Container>
        <Footer color="#fff" />
      </Wrap>
    </ThemeProvider>
  );
}

export default LiarGameLayout;
