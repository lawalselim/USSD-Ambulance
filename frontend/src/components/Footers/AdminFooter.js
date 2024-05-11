/*!

=========================================================
* Argon Dashboard React - v1.2.4
=========================================================

* Product Page: https://www.creative-tim.com/product/argon-dashboard-react
* Copyright 2024 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/argon-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
/*eslint-disable*/

// reactstrap components
import { Container, Row, Col, Nav, NavItem, NavLink } from "reactstrap";

const Footer = () => {
  return (
    <footer className="footer">
      <Row className="align-items-center justify-content-xl-between">
        <Col xl="6">
          <div className="copyright text-center text-xl-left text-muted">
            © {new Date().getFullYear()}{" "}
            <a
              className="font-weight-bold ml-1"
              href="https://uk.linkedin.com/in/selim-lawal-91b6b7131"
              rel="noopener noreferrer"
              target="_blank"
            >
              Selim Lawal
            </a>
          </div>
        </Col>

        <Col xl="6">
          <Nav className="nav-footer justify-content-center justify-content-xl-end">
            <NavItem>
              <NavLink
                href="https://github.com/lawalselim/USSD-Ambulance"
                rel="noopener noreferrer"
                target="_blank"
              >
                Github
              </NavLink>
            </NavItem>

            <NavItem>
              <NavLink
                href="https://selimlawal.netlify.app/"
                rel="noopener noreferrer"
                target="_blank"
              >
                About Me
              </NavLink>
            </NavItem>

            <NavItem>
              <NavLink
                href="http://blog.creative-tim.com?ref=adr-admin-footer"
                rel="noopener noreferrer"
                target="_blank"
              >
                Blog
              </NavLink>
            </NavItem>
          </Nav>
        </Col>
      </Row>
    </footer>
  );
};

export default Footer;
