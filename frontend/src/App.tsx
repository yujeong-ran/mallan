import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import GlobalStyle from './styles/globalStyle';
import HomeLayout from './components/layout/HomeLayout';
import Home from './pages/HomePage';
import LiarGameLayout from './components/layout/LiarGameLayout';
import LiarMainPage from './pages/liar/LiarMainPage';
import LiarRoomPage from './pages/liar/LiarRoomPage';
import LiarExplanationPage from './pages/liar/LiarExplanationPage';
import LiarVote from './pages/liar/LiarVote';
import LiarWordEnter from './pages/liar/LiarWordEnter';

function App() {
  return (
    <>
      <GlobalStyle />
      <Router>
        <Routes>
          <Route
            path="/"
            element={
              <HomeLayout>
                <Home />
              </HomeLayout>
            }
          />
          <Route element={<LiarGameLayout />}>
            <Route path="/liar/room" element={<LiarMainPage />} />
            <Route path="/liar/room/:roomId" element={<LiarMainPage />} />
            <Route path="/liar/lobby" element={<LiarRoomPage />} />
            <Route path="/liar/explanation" element={<LiarExplanationPage />} />
            <Route path="/liar/vote" element={<LiarVote />} />
            <Route path="/liar/wordEnter" element={<LiarWordEnter />} />
          </Route>
        </Routes>
      </Router>
    </>
  );
}

export default App;
