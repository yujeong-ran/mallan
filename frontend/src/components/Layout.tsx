import { Outlet } from 'react-router-dom';
import Footer from './Footer';
import styled from 'styled-components';
import Header from './Header';

const Wrap = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  padding: 0 36px;
  overflow: hidden;
`;

const Main = styled.main`
  width: 1200px;
  max-width: 100%;
  padding-top: 70px;
  margin: 0 auto;
`;

function HomeLayout() {
  return (
    <Wrap>
      <Header />
      <Main>
        <Outlet />
      </Main>
      <Footer />
    </Wrap>
  );
}

export default HomeLayout;
