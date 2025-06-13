import styled from 'styled-components';

const InputBox = styled.div`
  position: relative;
  width: 100%;
  height: 40px;
  margin-top: 40px;
  border: 1px solid ${({ theme }) => theme.border};
  border-radius: ${({ theme }) => theme.borderRadSm};
  overflow: hidden;

  label {
    position: absolute;
    z-index: 1;
    top: 5px;
    left: 5px;
    width: 66px;
    height: calc(100% - 10px);
    line-height: 30px;
    color: #fff;
    font-size: 14px;
    text-align: center;
    border-radius: ${({ theme }) => theme.borderRadXsm};
    background-color: ${({ theme }) => theme.point};
  }

  input {
    width: calc(100% - 94px);
    height: 100%;
    padding-left: 80px;
    padding-right: 14px;
    border: 0;
    background: #fff;

    &::placeholder {
      color: #ccc;
    }
  }
`;

function NickNameRegister({
  placeholder,
  nickname,
  setNickname,
}: {
  placeholder: string;
  nickname: string;
  setNickname: (value: string) => void;
}) {
  return (
    <InputBox>
      <label htmlFor="nickname">닉네임</label>
      <input
        id="nickname"
        type="text"
        maxLength={20}
        value={nickname}
        onChange={(e) => setNickname(e.target.value)}
        placeholder={placeholder}
      />
    </InputBox>
  );
}

export default NickNameRegister;
