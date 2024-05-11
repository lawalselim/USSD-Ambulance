import React, { useEffect, useState } from 'react';
import {
  Badge,
  Card,
  CardHeader,
  CardFooter,
  DropdownMenu,
  DropdownItem,
  UncontrolledDropdown,
  DropdownToggle,
  Media,
  Pagination,
  PaginationItem,
  PaginationLink,
  Table,
  Container,
  Row,
} from 'reactstrap';
import axios from 'axios'; // Ensure axios is installed
import StartCard from '../../components/Headers/StatCard'

const Bookings = () => {
  const [ambulances, setAmbulances] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 20; // Items per page
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    const fetchAmbulances = async () => {
      try {
        const response = await axios.get('http://localhost:8005/admins/ambulances');
        setAmbulances(response.data);
        setTotalPages(Math.ceil(response.data.length / pageSize));
        setLoading(false);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };

    fetchAmbulances();
  }, []);

  // Get current page data
  const currentData = ambulances.slice(
      (currentPage - 1) * pageSize,
      (currentPage - 1) * pageSize + pageSize
  );

  // Change page
  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
      <>
        <StartCard/>
        <Container className="mt--7" fluid>
          <Row>
            <div className="col">
              <Card className="shadow">
                <CardHeader className="border-0">
                  <h3 className="mb-0">Ambulance List</h3>
                </CardHeader>
                <Table className="align-items-center table-flush" responsive>
                  <thead className="thead-light">
                  <tr>
                    <th scope="col">License Plate</th>
                    <th scope="col">Assigned Status</th>
                    <th scope="col">Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  {loading ? (
                      <tr>
                        <td colSpan="3" className="text-center">
                          Loading ambulances...
                        </td>
                      </tr>
                  ) : error ? (
                      <tr>
                        <td colSpan="3" className="text-center text-danger">
                          Error: {error}
                        </td>
                      </tr>
                  ) : currentData.length > 0 ? (
                      currentData.map((ambulance) => (
                          <tr key={ambulance.id}>
                            <th scope="row">{ambulance.licensePlate}</th>
                            <td>
                              <Badge color={ambulance.isAssigned ? "success" : "danger"}>
                                {ambulance.isAssigned ? "Assigned" : "Unassigned"}
                              </Badge>
                            </td>
                            <td className="text-right">
                              <UncontrolledDropdown>
                                <DropdownToggle
                                    className="btn-icon-only text-light"
                                    href="#pablo"
                                    role="button"
                                    size="sm"
                                    color=""
                                    onClick={(e) => e.preventDefault()}
                                >
                                  <i className="fas fa-ellipsis-v" />
                                </DropdownToggle>
                                <DropdownMenu className="dropdown-menu-arrow" right>
                                  <DropdownItem
                                      href="#pablo"
                                      onClick={(e) => e.preventDefault()}
                                  >
                                    Re-assign
                                  </DropdownItem>
                                  <DropdownItem
                                      href="#pablo"
                                      onClick={(e) => e.preventDefault()}
                                  >
                                    Delete
                                  </DropdownItem>
                                </DropdownMenu>
                              </UncontrolledDropdown>
                            </td>
                          </tr>
                      ))
                  ) : (
                      <tr>
                        <td colSpan="3" className="text-center">
                          No ambulances found
                        </td>
                      </tr>
                  )}
                  </tbody>
                </Table>
                <CardFooter className="py-4">
                  <nav aria-label="...">
                    <Pagination
                        className="pagination justify-content-end mb-0"
                        listClassName="justify-content-end mb-0"
                    >
                      <PaginationItem disabled={currentPage <= 1}>
                        <PaginationLink
                            href="#pablo"
                            onClick={(e) => {
                              e.preventDefault();
                              paginate(currentPage - 1);
                            }}
                            tabIndex="-1"
                        >
                          <i className="fas fa-angle-left" />
                          <span className="sr-only">Previous</span>
                        </PaginationLink>
                      </PaginationItem>
                      {[...Array(totalPages).keys()].map((page) => (
                          <PaginationItem active={page + 1 === currentPage} key={page}>
                            <PaginationLink
                                href="#pablo"
                                onClick={(e) => {
                                  e.preventDefault();
                                  paginate(page + 1);
                                }}
                            >
                              {page + 1}
                            </PaginationLink>
                          </PaginationItem>
                      ))}
                      <PaginationItem disabled={currentPage >= totalPages}>
                        <PaginationLink
                            href="#pablo"
                            onClick={(e) => {
                              e.preventDefault();
                              paginate(currentPage + 1);
                            }}
                        >
                          <i className="fas fa-angle-right" />
                          <span className="sr-only">Next</span>
                        </PaginationLink>
                      </PaginationItem>
                    </Pagination>
                  </nav>
                </CardFooter>
              </Card>
            </div>
          </Row>
        </Container>
      </>
  );
};

export default Bookings;
