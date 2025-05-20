import styled from 'styled-components';

const LiarContainer = styled.div`
  max-width: 590px;
  width: 100%;
  margin: 0 auto;
`;

function ContContainer({ children }: { children: React.ReactNode }) {
  return <LiarContainer>{children}</LiarContainer>;
}

export default ContContainer;
