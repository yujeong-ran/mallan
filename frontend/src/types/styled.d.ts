import 'styled-components';
import { GameThemeType } from '../styles/gameThemes';

declare module 'styled-components' {
  export interface DefaultTheme extends GameThemeType {}
}
