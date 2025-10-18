import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor to handle token expiration
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authService = {
  login: async (loginRequest) => {
    const response = await api.post('/auth/login', loginRequest);
    return response.data;
  },

  register: async (registerRequest) => {
    const response = await api.post('/auth/register', registerRequest);
    return response.data;
  },
};

export const formService = {
  createForm: async (form) => {
    const response = await api.post('/forms', form);
    return response.data;
  },

  updateForm: async (formId, form) => {
    const response = await api.put(`/forms/${formId}`, form);
    return response.data;
  },

  deleteForm: async (formId) => {
    const response = await api.delete(`/forms/${formId}`);
    return response.data;
  },

  getMyForms: async () => {
    const response = await api.get('/forms/my-forms');
    return response.data;
  },

  getPublicForms: async () => {
    const response = await api.get('/forms/public');
    return response.data;
  },

  getFormById: async (formId) => {
    const response = await api.get(`/forms/${formId}`);
    return response.data;
  },

  getPublicFormById: async (formId) => {
    const response = await api.get(`/forms/public/${formId}`);
    return response.data;
  },
};

export const responseService = {
  submitResponse: async (formId, responseData, respondentEmail, respondentName) => {
    const response = await api.post(`/responses/submit/${formId}`, responseData, {
      params: { respondentEmail, respondentName }
    });
    return response.data;
  },

  getResponsesByForm: async (formId) => {
    const response = await api.get(`/responses/form/${formId}`);
    return response.data;
  },

  getResponsesByFormAndDateRange: async (formId, startDate, endDate) => {
    const response = await api.get(`/responses/form/${formId}/date-range`, {
      params: { startDate, endDate }
    });
    return response.data;
  },
};

export default api;
