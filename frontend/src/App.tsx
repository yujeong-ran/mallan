import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import GlobalStyle from './styles/globalStyle';
import Home from './pages/Home';
import HomeLayout from './components/Layout';

function App() {
  return (
    <>
      <GlobalStyle />
      <Router>
        <Routes>
          <Route element={<HomeLayout />}>
            <Route path="/" element={<Home />} />
          </Route>
        </Routes>
      </Router>
    </>
  );
}

export default App;
