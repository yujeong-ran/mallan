import { Outlet } from 'react-router-dom';
import Footer from './Footer';
import styled from 'styled-components';

const Wrap = styled.div`
  min-height: 100vh;
  background: linear-gradient(to bottom, #f5eaff, #fef8ff);
`;

const Main = styled.main`
  max-width: 1200px;
  margin: 0 auto;
`;

function Layout() {
  return (
    <Wrap>
      <Main>
        <Outlet />
      </Main>
      <Footer />
    </Wrap>
  );
}

export default Layout;
