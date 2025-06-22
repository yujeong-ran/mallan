import styled from 'styled-components';
import media from '../../styles/breakPoint';
import Timer from './Timer';
import { getWordApi } from '../../api/getWordApi';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const ExplanationCon = styled.div`
  padding: 40px;
  margin-bottom: 10px;
  border-radius: ${({ theme }) => theme.borderRad};
  background: #f7f7f7;

  ${media.medium`
    padding:30px;
  `}
`;

const Speaker = styled.div`
  text-align: center;

  > p {
    color: ${({ theme }) => theme.textPoint};
    font-size: 16px;
    margin-bottom: 14px;
  }
  > strong {
    font-size: 24px;
  }
`;

const WordGuide = styled.div`
  padding: 12px 0;
  color: ${({ theme }) => theme.textBase};
  text-align: center;
  border-radius: ${({ theme }) => theme.borderRadSm};
  background-color: #fff;

  span {
    color: ${({ theme }) => theme.point};
  }
`;

const Button = styled.button`
  width: 100%;
  height: 50px;
  margin-top: 10px;
  color: #fff;
  font-size: 16px;
  border-radius: ${({ theme }) => theme.borderRadSm};
  background: ${({ theme }) => theme.point};
`;

function Explanation() {
  const { roomCode } = useParams();
  const [word, setWord] = useState('');
  const playerNickname = localStorage.getItem('playerNickname');
  const playerId = localStorage.getItem('playerId');

  useEffect(() => {
    const fetchWord = async () => {
      try {
        const wordData = await getWordApi(roomCode ?? '', playerId ?? '');

        setWord(wordData.data.word);
      } catch (error) {
        console.log('요청 실패', error);
      }
    };
    fetchWord();
  }, []);

  return (
    <ExplanationCon>
      <Speaker>
        <p>현재 발언자</p>
        <strong>{playerNickname}</strong>
      </Speaker>
      <Timer />
      <WordGuide>
        <span>{word}</span>에 대해 설명하세요!
      </WordGuide>
      <Button>다음 사람에게 넘기기</Button>
    </ExplanationCon>
  );
}

export default Explanation;
