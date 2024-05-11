import React, { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';

// Fix the default icon problem in Leaflet 1.x when using webpack
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

const EmergencyMap = () => {
    const [locations, setLocations] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8005/admins/top-locations')
            .then(response => response.json())
            .then(data => {
                const mappedLocations = data.map(item => ({
                    address: item[0],
                    count: item[1],
                    lng: item[2],
                    lat: item[3]
                }));
                setLocations(mappedLocations);
            })
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <MapContainer center={[51.505, -0.09]} zoom={13} style={{ height: '500px', width: '100%', }}>
            <TileLayer
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            />
            {locations.map((loc, index) => (
                <Marker key={index} position={[loc.lat, loc.lng]}>
                    <Popup>
                        {loc.address} has {loc.count} emergency calls.
                    </Popup>
                </Marker>
            ))}
        </MapContainer>
    );
};

export default EmergencyMap;
