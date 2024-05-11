import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { GoogleMap, LoadScript, Marker } from '@react-google-maps/api';
import { Card, Container, Row, Col, ListGroup, ListGroupItem } from "reactstrap";
import StatCard from "../../components/Headers/StatCard.js";

const containerStyle = {
    width: '100%',
    height: '400px'
};

const BookingDetails = () => {
    const { bookingId } = useParams();
    const [booking, setBooking] = useState(null);

    useEffect(() => {
        const fetchBookingDetails = async () => {
            const response = await fetch(`http://localhost:8005/admins/booking/${bookingId}`);
            const data = await response.json();
            setBooking(data);
        };

        fetchBookingDetails();
    }, [bookingId]);

    if (!booking) {
        return <div>Loading...</div>;
    }

    const center = {
        lat: booking.latitude,
        lng: booking.longitude
    };

    return (
        <>
            <StatCard />
            <Container>
                <Row  style={{ marginTop: '50px' }}>
                    <Col md="6">
                        <Card>
                            <ListGroup>
                                <ListGroupItem>ID: {booking.id}</ListGroupItem>
                                <ListGroupItem>Address: {booking.address}</ListGroupItem>
                                <ListGroupItem>Emergency Type: {booking.emergencyTypeEnum}</ListGroupItem>
                                <ListGroupItem>User Name: {booking.user.name}</ListGroupItem>
                                <ListGroupItem>Status: {booking.status || 'N/A'}</ListGroupItem>
                                <ListGroupItem>Longitude:{booking.longitude}</ListGroupItem>
                                <ListGroupItem>Longitude:{booking.latitude}</ListGroupItem>
                                <ListGroupItem>Ambulance: {booking.ambulance ? booking.ambulance.licensePlate : 'N/A'}</ListGroupItem>
                            </ListGroup>
                        </Card>
                    </Col>
                    <Col md="6" style={{ width: '500px' }}>
                        <LoadScript
                            googleMapsApiKey="AIzaSyD40ABR79k9VKd7DXt8CSDat9ZIQxYZRNk">
                            <GoogleMap
                                mapContainerStyle={containerStyle}
                                center={center}
                                zoom={13}
                            >
                                <Marker position={center} />
                            </GoogleMap>
                        </LoadScript>
                    </Col>
                </Row>
            </Container>
        </>
    );
};

export default BookingDetails;
