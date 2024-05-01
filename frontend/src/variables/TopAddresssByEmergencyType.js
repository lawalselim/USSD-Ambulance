import React, { useEffect, useState } from 'react';
import { Bar } from 'react-chartjs-2';

const TopAddresssByEmergencyType = () => {
    const [chartData, setChartData] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch('http://localhost:8005/admins/top-addresses-by-type');
            const data = await response.json();
            formatChartData(data);
        };

        const formatChartData = (data) => {
            const emergencyTypes = [...new Set(data.map(item => item[0]))];
            const addresses = [...new Set(data.map(item => item[1]))];

            const datasets = emergencyTypes.map(type => ({
                label: type,
                data: addresses.map(address => {
                    const item = data.find(item => item[0] === type && item[1] === address);
                    return item ? item[2] : 0;
                }),
                backgroundColor: getRandomColor() // Assign a unique color for each emergency type
            }));

            setChartData({
                labels: addresses,
                datasets
            });
        };

        const getRandomColor = () => {
            return `rgba(${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, 0.6)`;
        };

        fetchData();
    }, []);

    const options = {
        responsive: true,
        scales: {
            xAxes: [{
                stacked: true, // Stacked option for the X-axis
            }],
            yAxes: [{
                stacked: true, // Stacked option for the Y-axis
            }]
        }
    };

    return (
        <div style={{ height: '300px', width: '100%', marginTop:"50px" }}>
            <h2>Emergency Requests by Type and Address</h2>
            <Bar data={chartData} options={options} />
        </div>
    );
};

export default TopAddresssByEmergencyType;
