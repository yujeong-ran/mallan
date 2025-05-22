import styled from 'styled-components';

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
  return (
    <ContWrap>
      <SetList>
        <li>
          <label htmlFor="category">카테고리 선택</label>
          <Select name="" id="category">
            <option value="">주제 선택</option>
            <option value="">주제 1</option>
            <option value="">주제 2</option>
            <option value="">주제 3</option>
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
