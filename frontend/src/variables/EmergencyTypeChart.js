import React, { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';

const EmergencyTypeChart = () => {
    const [chartData, setChartData] = useState({});
    const [legendItems, setLegendItems] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8005/admins/emergency-type-frequencies')
            .then(response => response.json())
            .then(data => {
                const emergencyTypes = data.map(item => item[0]);
                const frequencies = data.map(item => item[1]);
                const backgroundColors = [
                    'rgba(255, 99, 132, 0.6)',
                    'rgba(54, 162, 235, 0.6)',
                    'rgba(255, 206, 86, 0.6)',
                    'rgba(75, 192, 192, 0.6)',
                    'rgba(153, 102, 255, 0.6)',
                    'rgba(255, 159, 64, 0.6)'
                ];

                setChartData({
                    labels: emergencyTypes,
                    datasets: [
                        {
                            label: 'Number of Requests',
                            data: frequencies,
                            backgroundColor: backgroundColors,
                            borderColor: backgroundColors.map(color => color.replace('0.6', '1')),
                            borderWidth: 1
                        }
                    ]
                });

                // Set legend items for the footer
                setLegendItems(emergencyTypes.map((type, index) => ({
                    type,
                    color: backgroundColors[index]
                })));
            })
            .catch(error => console.error('Error fetching data: ', error));
    }, []);

    return (
        <div>
            <Pie data={chartData} options={{ responsive: true }} />
            <FooterLegend items={legendItems} />
        </div>
    );
};

const FooterLegend = ({ items }) => (
    <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
        {items.map(item => (
            <div key={item.type} style={{ marginRight: '20px', display: 'flex', alignItems: 'center' }}>
                <div style={{ width: '10px', height: '10px', backgroundColor: item.color, marginRight: '5px', marginLeft: '5px' }}></div>
                <span>{item.type}</span>
            </div>
        ))}
    </div>
);

export default EmergencyTypeChart;
