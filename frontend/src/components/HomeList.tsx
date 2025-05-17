import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { LiaRedhat } from 'react-icons/lia';

const ListStyle = styled.ul`
  display: flex;
  flex-wrap: wrap;
  margin: 70px -10px 0;

  li {
    width: calc(50% - 20px);
    margin: 0 10px 20px;
    color: #fff;
    min-height: 200px;
    border-radius: 10px;
    box-sizing: border-box;
    background: #0271e0;
    transform: translateY(0);
    transition: transform 0.3s ease;

    &.empty {
      background: #f5f5f5;

      p {
        display: flex;
        height: 100%;
        align-items: center;
        justify-content: center;
        color: #bbb;
      }
    }

    &:not(.empty):hover {
      transform: translateY(-20px);
    }

    a {
      display: flex;
      flex-direction: column;
      width: 100%;
      height: 100%;
      padding: 32px;
      box-sizing: border-box;

      h2 {
        display: flex;
        align-items: center;
        font-size: 20px;
        font-weight: 700;

        svg {
          margin-right: 6px;
          font-size: 20px;
        }
      }

      p {
        margin: 14px 0 30px;
        font-size: 15px;
        color: #cce3f9;
        line-height: 1.5;
      }
    }
  }
`;

const Tag = styled.div`
  display: flex;
  margin-top: auto;

  span {
    padding: 5px 8px;
    border-radius: 30px;
    color: #0271e0;
    font-size: 12px;
    text-align: center;
    line-height: 1;
    background: #fff;
  }
`;

function HomeList() {
  return (
    <ListStyle>
      <li>
        <Link to="/">
          <h2>
            <LiaRedhat />
            <span>라이어 게임</span>
          </h2>
          <p>
            한 명의 라이어를 찾아내거나, 다른 사람들을 속이고 승리해보세요!
            <br />
            추리력과 상상력을 발휘해 진실과 거짓을 가려내는 게임입니다.
          </p>
          <Tag>
            <span>4인 이상</span>
          </Tag>
        </Link>
      </li>
      <li className="empty">
        <p>서비스 준비 중입니다.</p>
      </li>
    </ListStyle>
  );
}

export default HomeList;
