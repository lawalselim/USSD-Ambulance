
import { useState } from "react";
// node.js library that concatenates classes (strings)
import classnames from "classnames";
// javascipt plugin for creating charts
import Chart from "chart.js";


// react plugin used to create charts
import { Line, Bar } from "react-chartjs-2";
// reactstrap components
import {
  Button,
  Card,
  CardHeader,
  CardBody,
  NavItem,
  NavLink,
  Nav,
  Progress,
  Table,
  Container,
  Row,
  Col,
} from "reactstrap";

// core components
import {
  chartOptions,
  parseOptions,
  chartExample1,
  chartExample2,
} from "variables/charts.js";

import StatCard from "../components/Headers/StatCard.js";
import EmergencyTypeChart from "../variables/EmergencyTypeChart";
import EmergencyMapFlasher from "../variables/EmergencyMapFlasher";
import TopAddresses from '../variables/TopAddresses';
import TopAddressByEmergencyType from "../variables/TopAddresssByEmergencyType"

// Showing address with request on the map

import EmergencyMap from '../variables/EmergencyMap';

const Dashboard = (props) => {
  const [activeNav, setActiveNav] = useState(1);
  const [chartExample1Data, setChartExample1Data] = useState("data1");

  if (window.Chart) {
    parseOptions(Chart, chartOptions());
  }

  const toggleNavs = (e, index) => {
    e.preventDefault();
    setActiveNav(index);
    setChartExample1Data("data" + index);
  };
  return (
    <>
      <StatCard />
      {/* Page content */}
      <Container className="mt--7" fluid>
        <Row>
          {/*Address Mapping for address with more emergencies*/}
          <Col className="mb-5 mb-xl-0" xl="8" style={{  marginTop: '30px' }}>
            <Card className="bg-gradient-default shadow">
              <CardHeader className="bg-transparent">
                <Row className="align-items-center">
                  <div className="col">
                    <h6 className="text-uppercase text-light ls-1 mb-1">
                      Overview
                    </h6>
                    <h2 className="text-white mb-0">New Emergency Flasher</h2>
                  </div>
                </Row>
              </CardHeader>
              <CardBody>
                {/* Chart */}
                <EmergencyMapFlasher />
              </CardBody>
            </Card>
          </Col>
          {/*Testing Top-address barchart*/}
          <Col xl="4">
            <Card className="shadow">
              <CardHeader className="bg-transparent">
                <Row className="align-items-center">
                  <div className="col">
                    <h6 className="text-uppercase text-muted ls-1 mb-1">
                      Performance
                    </h6>
                    <h2 className="mb-0">Total Bookings</h2>
                  </div>
                </Row>
              </CardHeader>
              <CardBody>
                {/* Chart */}
                <div className="chart">

                  <TopAddresses/>

                </div>
              </CardBody>
            </Card>
          </Col>
          {/*Testing the EmergencyChart*/}
          <Col xl="4" style={{  marginTop: '30px', width:"250px" }}>
            <Card className="shadow">
              <CardHeader className="bg-transparent">
                <Row className="align-items-center">
                  <div className="col">
                    <h6 className="text-uppercase text-muted ls-1 mb-1">
                      Performance
                    </h6>
                    <h2 className="mb-0">Emergency Type Frequency</h2>
                  </div>
                </Row>
              </CardHeader>
              <CardBody>
                {/* Chart */}
                <div className="chart">
                  <  EmergencyTypeChart />
                </div>
              </CardBody>
            </Card>
          </Col>
          {/*Address Mapping for address with more emergencies*/}
          <Col className="mb-5 mb-xl-0" xl="8" style={{  marginTop: '30px' }}>
            <Card className="bg-gradient-default shadow">
              <CardHeader className="bg-transparent">
                <Row className="align-items-center">
                  <div className="col">
                    <h6 className="text-uppercase text-light ls-1 mb-1">
                      Overview
                    </h6>
                    <h2 className="text-white mb-0">Emergency Heat Map</h2>
                  </div>
                </Row>
              </CardHeader>
              <CardBody>
                {/* Chart */}
                <EmergencyMap />
              </CardBody>
            </Card>
          </Col>
        </Row>
        <Container>

            <div >
              <TopAddresses/>
            </div>

        </Container>

        <Container>

            <div className="Col" style={{height: '300px', width: '100%'}}>
              <TopAddressByEmergencyType/>
            </div>

        </Container>
      </Container>
    </>
  );
};

export default Dashboard;
