import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';

const TopAddresses = () => {
    const [chartData, setChartData] = useState({
        labels: [],
        datasets: []
    });

    useEffect(() => {
        fetch('http://localhost:8005/admins/top-addresses')
            .then(response => response.json())
            .then(data => {
                setChartData({
                    labels: data.labels,
                    datasets: [
                        {
                            label: '# of Emergency Requests',
                            data: data.data,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.5)',
                                'rgba(54, 162, 235, 0.5)',
                                'rgba(255, 206, 86, 0.5)',
                                'rgba(75, 192, 192, 0.5)',
                                'rgba(153, 102, 255, 0.5)',
                                'rgba(255, 159, 64, 0.5)'
                            ],
                            hoverBackgroundColor: [
                                'rgba(255, 99, 132, 0.7)',
                                'rgba(54, 162, 235, 0.7)',
                                'rgba(255, 206, 86, 0.7)',
                                'rgba(75, 192, 192, 0.7)',
                                'rgba(153, 102, 255, 0.7)',
                                'rgba(255, 159, 64, 0.7)'
                            ],
                            borderWidth: 1
                        }
                    ]
                });
            })
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    const options = {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
            display: true,
            position: 'bottom',
            labels: {
                fontColor: '#333',
                fontSize: 14,
                boxWidth: 20
            }
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    };

    return (
        <div style={{ height: '300px', width: '100%', marginTop:"50px" }}>
            <h2>Top Emergency Addresses</h2>
            <Bar data={chartData} options={options} />
        </div>
    );
};

export default TopAddresses;
