export const gameThemes = {
  liar: {
    point: '#800000',
    subPoint: '#6B0404',
    subBg: '#f7f7f7',
    textBase: '#1B1718',
    textMuted: '#9C9B9B',
    textPoint: '#982B1C',
    textPlaceholder: '#e6e6e6',
    border: '#eee',
    borderRad: '10px',
    borderRadSm: '6px',
    borderRadXsm: '4px',
  },
} as const;

export type GameThemeType = typeof gameThemes.liar;
