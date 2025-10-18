import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { formService } from '../services/api';
import { EyeIcon } from '@heroicons/react/24/outline';

const PublicForms = () => {
  const [forms, setForms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchPublicForms();
  }, []);

  const fetchPublicForms = async () => {
    try {
      const data = await formService.getPublicForms();
      setForms(data);
    } catch (err) {
      setError(err.response?.data || 'Failed to fetch forms');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-lg">Loading forms...</div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Public Forms</h1>
        <p className="mt-2 text-gray-600">Browse and fill out publicly available forms</p>
      </div>

      {error && (
        <div className="mb-4 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded">
          {error}
        </div>
      )}

      {forms.length === 0 ? (
        <div className="text-center py-12">
          <div className="text-gray-500 text-lg">No public forms available</div>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {forms.map((form) => (
            <div key={form.id} className="bg-white rounded-lg shadow-md p-6">
              <div className="mb-4">
                <h3 className="text-lg font-semibold text-gray-900 mb-2">{form.title}</h3>
                {form.description && (
                  <p className="text-gray-600 text-sm">{form.description}</p>
                )}
              </div>
              
              <div className="flex items-center justify-between text-sm text-gray-500 mb-4">
                <span>By {form.creatorUsername}</span>
                <span className="px-2 py-1 rounded-full text-xs bg-green-100 text-green-800">
                  Active
                </span>
              </div>
              
              <div className="flex space-x-2">
                <Link
                  to={`/forms/${form.id}`}
                  className="flex-1 inline-flex items-center justify-center px-3 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700"
                >
                  <EyeIcon className="h-4 w-4 mr-2" />
                  Fill Form
                </Link>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default PublicForms;
