import styled from 'styled-components';
import avatar1 from '../../assets/image/avatar1.png';
import avatar2 from '../../assets/image/avatar2.png';
import avatar3 from '../../assets/image/avatar3.png';
import avatar4 from '../../assets/image/avatar4.png';

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

  img {
    width: 100%;
    display: block;
  }
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
    cursor: pointer;

    img {
      width: 100%;
      display: block;
    }
  }
`;

function CharacterRegister({
  profileImg,
  setProfileImg,
}: {
  profileImg: string;
  setProfileImg: (img: string) => void;
}) {
  return (
    <CharacterRegisterWrap>
      <PreviewImg>
        <img src={profileImg} alt="" />
      </PreviewImg>
      <SelectImg>
        {[avatar1, avatar2, avatar3, avatar4].map((avatar, i) => (
          <div key={i} onClick={() => setProfileImg(avatar)}>
            <img src={avatar} alt="" />
          </div>
        ))}
      </SelectImg>
    </CharacterRegisterWrap>
  );
}

export default CharacterRegister;
