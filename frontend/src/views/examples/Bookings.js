import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
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
import { getAllBookings } from "../../service/BookingService";

const Bookings = () => {
    const [bookings, setBookings] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage] = useState(20);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchBookings = async () => {
            const data = await getAllBookings();
            if (data) setBookings(data);
        };
        fetchBookings();
    }, []);

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentBookings = bookings.slice(indexOfFirstItem, indexOfLastItem);

    const paginate = pageNumber => setCurrentPage(pageNumber);
    const totalPages = Math.ceil(bookings.length / itemsPerPage);

    // Function to handle the display of page numbers
    const pageNumbers = [];
    const visiblePages = 5; // Number of pages to show in the pagination component

    // Calculate range of page numbers to display
    let startPage = Math.max(currentPage - Math.floor(visiblePages / 2), 1);
    let endPage = startPage + visiblePages - 1;

    // Adjust if endPage exceeds the totalPages
    if (endPage > totalPages) {
        endPage = totalPages;
        startPage = Math.max(endPage - visiblePages + 1, 1);
    }

    for (let i = startPage; i <= endPage; i++) {
        pageNumbers.push(i);
    }

    return (
        <>
            <StatCard />
            <Container className="mt--7" fluid>
                <Row>
                    <div className="col">
                        <Card className="shadow">
                            <CardHeader className="border-0">
                                <h3 className="mb-0">Emergency Bookings</h3>
                            </CardHeader>
                            <Table className="align-items-center table-flush" responsive>
                                <thead className="thead-light">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Address</th>
                                    <th scope="col">Emergency Type</th>
                                    <th scope="col">Latitude</th>
                                    <th scope="col">Longitude</th>
                                    <th scope="col">User Name</th>
                                    <th scope="col">Status</th>
                                    <th scope="col">Ambulance License Plate</th>
                                    <th scope="col">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                {currentBookings.map(booking => (
                                    <tr key={booking.id} onClick={() => console.log("bookingID", booking.id)} style={{ cursor: "pointer" }}>
                                        <th scope="row">{booking.id}</th>
                                        <td>{booking.address}</td>
                                        <td>{booking.emergencyTypeEnum}</td>
                                        <td>{booking.latitude}</td>
                                        <td>{booking.longitude}</td>
                                        <td>{booking.user.name}</td>
                                        <td>{booking.status || 'N/A'}</td>
                                        <td>{booking.ambulance ? booking.ambulance.licensePlate : 'N/A'}</td>
                                        <td>
                                            <a href="#!" onClick={(e) => {
                                                e.preventDefault();
                                                navigate(`/admin/booking-details/${booking.id}`);
                                            }}>View</a>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </Table>
                            <CardFooter className="py-4">
                                <nav aria-label="...">
                                    <Pagination className="pagination justify-content-end mb-0">
                                        <PaginationItem disabled={currentPage === 1}>
                                            <PaginationLink href="#!" onClick={(e) => {
                                                e.preventDefault();
                                                paginate(1);
                                            }}>
                                                First
                                            </PaginationLink>
                                        </PaginationItem>
                                        {pageNumbers.map(number => (
                                            <PaginationItem key={number} className={currentPage === number ? 'active' : ''}>
                                                <PaginationLink onClick={() => paginate(number)} href="#!">
                                                    {number}
                                                </PaginationLink>
                                            </PaginationItem>
                                        ))}
                                        <PaginationItem disabled={currentPage === totalPages}>
                                            <PaginationLink href="#!" onClick={(e) => {
                                                e.preventDefault();
                                                paginate(totalPages);
                                            }}>
                                                Last
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
