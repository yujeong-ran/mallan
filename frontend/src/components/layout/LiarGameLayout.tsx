import { Outlet } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import { gameThemes } from '../../styles/gameThemes';

function LiarGameLayout() {
  return (
    <ThemeProvider theme={gameThemes.liar}>
      <Outlet />
    </ThemeProvider>
  );
}

export default LiarGameLayout;
