import React, { createContext, useContext, useState } from 'react';
import axios from 'axios';
import { Navigate, useNavigate } from 'react-router-dom';

// Create a context for the authentication state
const AuthContext = createContext(null);

// Axios instance for API requests
const api = axios.create({
    baseURL: 'http://localhost:8005/', // Replace with your API URL
    headers: {
        'Content-Type': 'application/json'
    }
});

// Authentication Provider Component
const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    const login = async (email, password) => {
        try {
            const response = await api.post('auth/login', { email, password });
            const { token } = response.data;
            localStorage.setItem('jwt', token);  // Store JWT in local storage
            setUser({ email, token });
            navigate('/dashboard');  // Redirect to dashboard after login
        } catch (error) {
            console.error('Login failed:', error);
            // Handle errors (e.g., show an error message)
        }
    };

    const logout = () => {
        localStorage.removeItem('jwt');
        setUser(null);
        navigate('/admin/dashboard');
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

// Hook to use authentication context
const useAuth = () => useContext(AuthContext);

// Component to protect routes that require authentication
const ProtectedRoute = ({ children }) => {
    const { user } = useAuth();
    if (!user) {
        return <Navigate to="/admin/dashboard" replace />;
    }
    return children;
};

// Export everything needed by other components
export { AuthProvider, useAuth, ProtectedRoute };