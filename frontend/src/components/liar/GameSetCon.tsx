import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { getTopicApi } from '../../api/getTopicApi';

interface TopicType {
  id: number;
  name: string;
}

const ContWrap = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const SetList = styled.ul`
  li label {
    display: block;
    margin-bottom: 10px;
  }
  li + li {
    margin-top: 30px;
  }
`;

const Select = styled.select`
  width: 100%;
  padding: 10px 14px;
  font-size: 16px;
  border-radius: ${({ theme }) => theme.borderRadSm};
  border: 1px solid ${({ theme }) => theme.border};
  background: #fff url('/image/select_arw.svg') no-repeat center right 10px;
`;

function GameSetCon() {
  const [topics, setTopics] = useState<TopicType[]>([]);
  const [selectedTopic, setSelectedTopic] = useState<number | null>(null);

  useEffect(() => {
    const topics = async () => {
      const res = await getTopicApi();
      //console.log(res);

      setTopics(res);
    };
    topics();
  }, []);

  const handleChnage = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedTopic(Number(e.target.value));
  };

  // 확인용
  // useEffect(() => {
  //   console.log(selectedTopic);
  // }, [selectedTopic]);

  return (
    <ContWrap>
      <SetList>
        <li>
          <label htmlFor="category">주제 선택</label>
          <Select
            name=""
            id="category"
            onChange={handleChnage}
            value={selectedTopic ?? ''}
          >
            <option value="">주제 선택</option>
            {topics.map((topic) => {
              return (
                <option key={topic.id} value={topic.id}>
                  {topic.name}
                </option>
              );
            })}
          </Select>
        </li>
        <li>
          <label htmlFor="round">설명 라운드</label>
          <Select name="" id="round">
            <option value="">2</option>
            <option value="">3</option>
            <option value="">4</option>
          </Select>
        </li>
      </SetList>
    </ContWrap>
  );
}

export default GameSetCon;
