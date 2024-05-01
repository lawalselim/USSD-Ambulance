import React, { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Marker, useMap } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';

const EmergencyMapFlasher = () => {
    const [emergencyRequests, setEmergencyRequests] = useState([]);

    useEffect(() => {
        const eventSource = new EventSource('http://localhost:8080/admins/emergency/requests/stream');
        eventSource.onmessage = function(event) {
            const newRequest = JSON.parse(event.data);
            setEmergencyRequests((existingRequests) => [...existingRequests, newRequest]);
        };

        return () => {
            eventSource.close();
        };
    }, []);

    return (
        <MapContainer center={[51.505, -0.09]} zoom={13} style={{ height: '300px', width: '100%' }}>
            <TileLayer
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            />
            {emergencyRequests.map((request, idx) => (
                <Marker key={idx} position={[request.latitude, request.longitude]}>
                    {/* Custom marker logic here */}
                </Marker>
            ))}
        </MapContainer>
    );
};

export default EmergencyMapFlasher;
