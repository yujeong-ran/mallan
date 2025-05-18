import styled from 'styled-components';
import media from '../../styles/breakPoint';
import Header from '../Header';
import Footer from '../Footer';

const Wrap = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  padding: 0 36px;
  overflow: hidden;

  ${media.medium`
      padding: 0 24px;
  `}
`;

const Main = styled.main`
  width: 1200px;
  max-width: 100%;
  padding-top: 70px;
  margin: 0 auto;

  ${media.medium`
      padding-top:50px;
  `}
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
