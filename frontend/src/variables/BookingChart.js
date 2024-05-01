import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import { Bar } from 'react-chartjs-2';
import 'chart.js';
const BookingChart = () => {
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [chartData, setChartData] = useState({});

    // Function to fetch data
    const fetchData = async (start, end) => {
        const url = `http://localhost:8005/admins/chart/`;
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const text = await response.text();
            if (text) {
                const data = JSON.parse(text);
                const labels = data.map(item => new Date(item[1]).toLocaleDateString());
                const counts = data.map(item => item[0]);
                setChartData({
                    labels,
                    datasets: [{
                        label: 'Number of Bookings',
                        data: counts,
                        backgroundColor: 'rgba(75, 192, 192, 0.5)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                });
            } else {
                console.log('No JSON response body.');
            }
        } catch (error) {
            console.error('Failed to fetch data:', error);
        }
    };

    // Effect to fetch data when dates change
    useEffect(() => {
        fetchData(startDate, endDate);
    }, [startDate, endDate]);

    return (
        <div>
            <h2>Bookings by Date</h2>
            <div>
                <DatePicker
                    selected={startDate}
                    onChange={date => setStartDate(date)}
                    selectsStart
                    startDate={startDate}
                    endDate={endDate}
                />
                <DatePicker
                    selected={endDate}
                    onChange={date => setEndDate(date)}
                    selectsEnd
                    startDate={startDate}
                    endDate={endDate}
                    minDate={startDate}
                />
            </div>
            <Bar data={chartData} />
        </div>
    );
};

export default BookingChart;
