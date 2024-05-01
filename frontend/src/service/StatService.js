
// In StatService.js
export const getAllStats = async () => {
    return fetch('http://localhost:8005/admins/dashboard')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .catch(error => {
            console.error('Error fetching Stats:', error);
            return []; // Return an empty array as fallback
        });
};

