import styled from 'styled-components';

interface FooterProps {
  color?: string;
}

const FooterContainer = styled.footer<FooterProps>`
  padding: 50px 0;
  margin-top: auto;
  color: ${({ color }) => color || '#1b1718'};
  font-size: 14px;
  text-align: center;
`;

function Footer({ color }: FooterProps) {
  return (
    <FooterContainer color={color}>
      © 2025 yujeongran game. All rights reserved.
    </FooterContainer>
  );
}

export default Footer;
