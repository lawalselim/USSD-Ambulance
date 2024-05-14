import React, { useEffect, useState } from 'react';
import { GoogleMap, LoadScript, Marker, InfoWindow } from '@react-google-maps/api';

const mapContainerStyle = {
    height: "500px",
    width: "100%"
};

const center = {
    lat: 51.505,
    lng: -0.09
};

const EmergencyMap = () => {
    const [locations, setLocations] = useState([]);
    const [selected, setSelected] = useState(null);

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
        <LoadScript
            googleMapsApiKey="AIzaSyD40ABR79k9VKd7DXt8CSDat9ZIQxYZRNk"
        >
            <GoogleMap
                mapContainerStyle={mapContainerStyle}
                center={center}
                zoom={13}
            >
                {locations.map((loc, index) => (
                    <Marker
                        key={index}
                        position={{ lat: loc.lat, lng: loc.lng }}
                        onClick={() => setSelected(loc)}
                    />
                ))}
                {selected ? (
                    <InfoWindow
                        position={{ lat: selected.lat, lng: selected.lng }}
                        onCloseClick={() => setSelected(null)}
                    >
                        <div>
                            {selected.address} has {selected.count} emergency calls.
                        </div>
                    </InfoWindow>
                ) : null}
            </GoogleMap>
        </LoadScript>
    );
};

export default EmergencyMap;
