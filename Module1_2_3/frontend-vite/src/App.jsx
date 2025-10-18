import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import FormBuilder from './pages/FormBuilder';
import FormView from './pages/FormView';
import PublicForms from './pages/PublicForms';
import ProtectedRoute from './components/ProtectedRoute';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-gray-50">
          <Navbar />
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/" element={<Navigate to="/dashboard" replace />} />
            <Route path="/dashboard" element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            } />
            <Route path="/form-builder" element={
              <ProtectedRoute>
                <FormBuilder />
              </ProtectedRoute>
            } />
            <Route path="/form-builder/:formId" element={
              <ProtectedRoute>
                <FormBuilder />
              </ProtectedRoute>
            } />
            <Route path="/forms/public" element={<PublicForms />} />
            <Route path="/forms/:formId" element={<FormView />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
