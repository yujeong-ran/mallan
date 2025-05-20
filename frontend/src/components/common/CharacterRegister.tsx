import styled from 'styled-components';

const CharacterRegisterWrap = styled.div`
  display: flex;
  flex-direction: column;
`;

const PreviewImg = styled.div`
  width: 70px;
  height: 70px;
  margin: 0 auto;
  border-radius: 100%;
  background-color: #fff;
  overflow: hidden;
`;

const SelectImg = styled.div`
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 20px;

  > div {
    width: 40px;
    height: 40px;
    border-radius: 100%;
    background-color: #fff;
    overflow: hidden;
  }
`;

function CharacterRegister() {
  return (
    <CharacterRegisterWrap>
      <PreviewImg></PreviewImg>
      <SelectImg>
        <div />
        <div />
        <div />
        <div />
      </SelectImg>
    </CharacterRegisterWrap>
  );
}

export default CharacterRegister;
