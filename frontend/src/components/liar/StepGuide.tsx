import styled from 'styled-components';
import media from '../../styles/breakPoint';

const GuideBox = styled.div`
  display: flex;
  align-items: flex-start;
  padding: 24px;
  margin-bottom: 16px;
  border-radius: ${({ theme }) => theme.borderRad};
  background: #f7f7f7;

  ${media.medium`
      display:block;
    `}

  > span {
    display: inline-block;
    padding: 6px 10px;
    margin-right: 20px;
    margin-bottom: 10px;
    color: ${({ theme }) => theme.point};
    border-radius: 40px;
    border: 1px solid #d9d9d9;
    background-color: #f4f2f2;

    ${media.medium`
      font-size:14px;
    `}
  }

  > p {
    line-height: 1.5;
  }
`;

interface StepGuideProps {
  title: string;
  description: React.ReactNode;
}

function StepGuide({ title, description }: StepGuideProps) {
  return (
    <GuideBox>
      <span>{title}</span>
      <p>{description}</p>
    </GuideBox>
  );
}

export default StepGuide;
