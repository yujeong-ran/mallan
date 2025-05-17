import styled from 'styled-components';
import Header from '../Header';
import Footer from '../Footer';

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

function HomeLayout({ children }: { children: React.ReactNode }) {
  return (
    <Wrap>
      <Header />
      <Main>{children}</Main>
      <Footer />
    </Wrap>
  );
}

export default HomeLayout;
