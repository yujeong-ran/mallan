import { useEffect, useState } from 'react';
import styled from 'styled-components';
import media from '../../styles/breakPoint';

const TimerWrap = styled.div`
  position: relative;
  width: 120px;
  height: 120px;
  margin: 40px auto;

  ${media.medium`
    margin: 26px auto;
  `}
`;

const TimeText = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 40px;
  font-weight: bold;
`;

const StyledSvg = styled.svg`
  width: 120px;
  height: 120px;
  transform: rotate(-90deg);
`;

const CircleBg = styled.circle`
  stroke: #e5e7eb;
  stroke-width: 12;
  fill: none;
`;

const CircleProgress = styled.circle`
  stroke: ${({ theme }) => theme.point};
  stroke-width: 12;
  fill: none;
  stroke-dasharray: 283;
  stroke-dashoffset: 141.5;
  stroke-linecap: round;
`;

function Timer() {
  const radius = 45;
  const center = 60;

  const [time, setTime] = useState(10);

  useEffect(() => {
    const timer = setInterval(() => {
      setTime((prev) => {
        if (prev <= 1) {
          clearInterval(timer);
          return 0;
        }
        return prev - 1;
      });
    }, 1000);
  }, []);

  return (
    <TimerWrap>
      <StyledSvg>
        <CircleBg r={radius} cx={center} cy={center} />
        <CircleProgress r={radius} cx={center} cy={center} />
      </StyledSvg>
      <TimeText>{time}</TimeText>
    </TimerWrap>
  );
}

export default Timer;
