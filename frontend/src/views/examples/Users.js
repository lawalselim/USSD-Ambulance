// import React, { useState, useEffect } from 'react';
// import {
//     Badge,
//     Card,
//     CardHeader,
//     CardFooter,
//     DropdownMenu,
//     DropdownItem,
//     UncontrolledDropdown,
//     DropdownToggle,
//     Media,
//     Pagination,
//     PaginationItem,
//     PaginationLink,
//     Table,
//     Container,
//     Row,
// } from "reactstrap";
// import StatCard from "../../components/Headers/StatCard.js";
// import {getAllBookings} from "../../service/BookingService";
//
// const Bookings = () => {
//     const [bookings, setBookings] = useState([]);
//     const [isLoading, setIsLoading] = useState(true);
//     const [error, setError] = useState(null);
//
//     useEffect(() => {
//         const fetchBookings = async () => {
//             try {
//                 setIsLoading(true);
//                 const data = await getAllBookings();
//                 setBookings(data);
//                 setIsLoading(false);
//             } catch (error) {
//                 console.error('Error fetching bookings:', error);
//                 setError(error);
//                 setIsLoading(false);
//             }
//         };
//
//         fetchBookings();
//     }, []);
//
//     if (error) {
//         return <div>Error: {error.message}</div>;
//     }
//
//     if (isLoading) {
//         return <div>Loading...</div>;
//     }
//
//     return (
//         <>
//             <StatCard />
//
//             {/* Page content */}
//             <Container className="mt--7" fluid>
//                 {/* Table */}
//                 <Row>
//                     <div className="col">
//                         <Card className="shadow">
//                             <CardHeader className="border-0">
//                                 <h3 className="mb-0">Bookings</h3>
//                             </CardHeader>
//                             <Table className="align-items-center table-flush" responsive>
//                                 <thead className="thead-light">
//                                 <tr>
//                                     <th scope="col">ID</th>
//                                     <th scope="col">Address</th>
//                                     <th scope="col">Emergency Type</th>
//                                     <th scope="col">Latitude</th>
//                                     <th scope="col">Longitude</th>
//                                     <th scope="col">User</th>
//                                     <th scope="col">Status</th>
//                                     <th scope="col">Ambulance Assigned</th>
//                                     <th scope="col">Action</th>
//                                     <th scope="col"/>
//                                 </tr>
//                                 </thead>
//                                 <tbody>
//                                 {bookings.map((booking) => (
//                                     <tr key={booking.id}>
//                                         <th scope="row">
//                                             <Media className="align-items-center">
//                           <span className="mb-0 text-sm">
//                             {booking.id}
//                           </span>
//                                             </Media>
//                                         </th>
//                                         <td>{booking.address}</td>
//                                         <td>
//                                             <Badge color="" className="badge-dot">
//                                                 <i className={booking.emergencyType === 'Heart-Attack' ? "bg-warning" : "bg-info"}/>
//                                                 {booking.emergencyType}
//                                             </Badge>
//                                         </td>
//                                         <td>{booking.latitude}</td>
//                                         <td>{booking.longitude}</td>
//                                         <td>{booking.user}</td>
//                                         <td>{booking.status}</td>
//                                         <td>{booking.ambulanceAssigned}</td>
//                                         <td className="text-right">
//                                             <UncontrolledDropdown>
//                                                 <DropdownToggle
//                                                     className="btn-icon-only text-light"
//                                                     role="button"
//                                                     size="sm"
//                                                     color=""
//                                                 >
//                                                     <i className="fas fa-ellipsis-v"/>
//                                                 </DropdownToggle>
//                                                 <DropdownMenu right>
//                                                     <DropdownItem onClick={(e) => e.preventDefault()}>
//                                                         Action
//                                                     </DropdownItem>
//                                                     <DropdownItem onClick={(e) => e.preventDefault()}>
//                                                         Another action
//                                                     </DropdownItem>
//                                                     <DropdownItem onClick={(e) => e.preventDefault()}>
//                                                         Something else here
//                                                     </DropdownItem>
//                                                 </DropdownMenu>
//                                             </UncontrolledDropdown>
//                                         </td>
//                                     </tr>
//                                 ))}
//                                 </tbody>
//                             </Table>
//                             <CardFooter className="py-4">
//                                 <nav aria-label="...">
//                                     <Pagination
//                                         className="pagination justify-content-end mb-0"
//                                         listClassName="justify-content-end mb-0"
//                                     >
//                                         <PaginationItem>
//                                             <PaginationLink
//                                                 href="#pablo"
//                                                 onClick={(e) => e.preventDefault()}
//                                             >
//                                                 <i className="fas fa-angle-left" />
//                                                 <span className="sr-only">Previous</span>
//                                             </PaginationLink>
//                                         </PaginationItem>
//                                         <PaginationItem className="active">
//                                             <PaginationLink
//                                                 href="#pablo"
//                                                 onClick={(e) => e.preventDefault()}
//                                             >
//                                                 1
//                                             </PaginationLink>
//                                         </PaginationItem>
//                                         <PaginationItem>
//                                             <PaginationLink
//                                                 href="#pablo"
//                                                 onClick={(e) => e.preventDefault()}
//                                             >
//                                                 2 <span className="sr-only">(current)</span>
//                                             </PaginationLink>
//                                         </PaginationItem>
//                                         <PaginationItem>
//                                             <PaginationLink
//                                                 href="#pablo"
//                                                 onClick={(e) => e.preventDefault()}
//                                             >
//                                                 3
//                                             </PaginationLink>
//                                         </PaginationItem>
//                                         <PaginationItem>
//                                             <PaginationLink
//                                                 href="#pablo"
//                                                 onClick={(e) => e.preventDefault()}
//                                             >
//                                                 <i className="fas fa-angle-right" />
//                                                 <span className="sr-only">Next</span>
//                                             </PaginationLink>
//                                         </PaginationItem>
//                                     </Pagination>
//                                 </nav>
//                             </CardFooter>
//                         </Card>
//                     </div>
//                 </Row>
//             </Container>
//         </>
//     );
// };
//
// export default Bookings;


import React, { useEffect, useState } from 'react';
import {
  Card,
  CardHeader,
  CardFooter,
  Table,
  Container,
  Row,
  Pagination,
  PaginationItem,
  PaginationLink
} from "reactstrap";
import StatCard from "../../components/Headers/StatCard.js";
import {getAllUsers} from "../../service/UserService";

const Users = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const data = await getAllUsers();
        console.log("Fetched Users data:", data);  // Check the fetched data
        if (data) setUsers(data);
        else setUsers([]);  // Set to empty array if data is undefined or null
      } catch (error) {
        console.error('Error fetching Users:', error);
        setUsers([]);  // Ensure bookings is set to an empty array on error
      }
    };

    fetchUsers();
  }, []);;

  return (
      <>
        <StatCard />
        <Container className="mt--7" fluid>
          <Row>
            <div className="col">
              <Card className="shadow">
                <CardHeader className="border-0">
                  <h3 className="mb-0">All Users</h3>
                </CardHeader>
                <Table className="align-items-center table-flush" responsive>
                  <thead className="thead-light">
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Phone Number</th>
                    <th scope="col">Date Created</th>
                    <th scope="col">Date Updated</th>
                    <th scope="col">Role</th>
                    <th scope="col">Action</th>
                  </tr>
                  </thead>
                  <tbody>
                  {users.map(user => (
                      <tr key={user.id}>
                        <th scope="row">{user.id}</th>
                        <td>{user.name}</td>
                        <td>{user.email}</td>
                        <td>{user.phoneNumber}</td>
                        <td>{user.createdAt}</td>
                        <td>{user.updatedAt}</td>
                        <td>{user.role.name}</td>

                        {/*<td>{user.ambulance ? booking.ambulance.licensePlate : 'N/A'}</td>*/}
                        <td>
                          {/* Implement any action button or link */}
                          <button>Edit</button>
                        </td>
                      </tr>
                  ))}
                  </tbody>
                </Table>
                <CardFooter className="py-4">
                  <Pagination className="pagination justify-content-end mb-0">
                    <PaginationItem className="disabled">
                      <PaginationLink href="#!" onClick={(e) => e.preventDefault()}>
                        <i className="fas fa-angle-left"/>
                        <span className="sr-only">Previous</span>
                      </PaginationLink>
                    </PaginationItem>
                    <PaginationItem className="active">
                      <PaginationLink href="#!" onClick={(e) => e.preventDefault()}>
                        1
                      </PaginationLink>
                    </PaginationItem>
                    <PaginationItem>
                      <PaginationLink href="#!" onClick={(e) => e.preventDefault()}>
                        <i className="fas fa-angle-right" />
                        <span className="sr-only">Next</span>
                      </PaginationLink>
                    </PaginationItem>
                  </Pagination>
                </CardFooter>
              </Card>
            </div>
          </Row>
        </Container>
      </>
  );
};

export default Users;
