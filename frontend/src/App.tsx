import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import GlobalStyle from './styles/globalStyle';
import HomeLayout from './components/layout/HomeLayout';
import Home from './pages/HomePage';
import LiarGameLayout from './components/layout/LiarGameLayout';
import LiarMainPage from './pages/liar/LiarMainPage';

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
            <Route path="/liar" element={<LiarMainPage />} />
          </Route>
        </Routes>
      </Router>
    </>
  );
}

export default App;
