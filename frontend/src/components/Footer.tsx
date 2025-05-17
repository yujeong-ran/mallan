import styled from 'styled-components';

const FooterContainer = styled.footer`
  padding: 50px 0;
  margin-top: auto;
  font-size: 14px;
  text-align: center;
`;

function Footer() {
  return (
    <FooterContainer>
      © 2025 yujeongran game. All rights reserved.
    </FooterContainer>
  );
}

export default Footer;
