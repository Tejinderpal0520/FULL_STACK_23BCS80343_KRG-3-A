import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { formService, responseService } from '../services/api';
import { useAuth } from '../contexts/AuthContext';

const FormView = () => {
  const { formId } = useParams();
  const { isAuthenticated } = useAuth();
  const [form, setForm] = useState(null);
  const [responses, setResponses] = useState({});
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    if (formId) {
      fetchForm();
    }
  }, [formId]);

  const fetchForm = async () => {
    try {
      const data = await formService.getPublicFormById(parseInt(formId));
      setForm(data);
    } catch (err) {
      setError(err.response?.data || 'Failed to fetch form');
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (fieldId, value) => {
    setResponses(prev => ({
      ...prev,
      [fieldId]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    setError('');

    try {
      await responseService.submitResponse(
        parseInt(formId),
        responses,
        responses.email || undefined,
        responses.name || undefined
      );
      setSuccess(true);
    } catch (err) {
      setError(err.response?.data || 'Failed to submit response');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-lg">Loading form...</div>
      </div>
    );
  }

  if (!form) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-lg text-red-600">Form not found</div>
      </div>
    );
  }

  if (success) {
    return (
      <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="bg-green-50 border border-green-200 rounded-lg p-6 text-center">
          <div className="text-green-600 text-lg font-semibold mb-2">Thank you!</div>
          <div className="text-green-700">Your response has been submitted successfully.</div>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="bg-white shadow rounded-lg p-6">
        <div className="mb-6">
          <h1 className="text-2xl font-bold text-gray-900 mb-2">{form.title}</h1>
          {form.description && (
            <p className="text-gray-600">{form.description}</p>
          )}
        </div>

        {error && (
          <div className="mb-4 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6">
          {form.fields?.map((field, index) => (
            <FieldRenderer
              key={field.id || index}
              field={field}
              value={responses[field.id]}
              onChange={(value) => handleInputChange(field.id, value)}
            />
          ))}

          <div className="flex justify-end">
            <button
              type="submit"
              disabled={submitting}
              className="px-6 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 disabled:opacity-50"
            >
              {submitting ? 'Submitting...' : 'Submit'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

const FieldRenderer = ({ field, value, onChange }) => {
  const renderField = () => {
    switch (field.fieldType) {
      case 'TEXT':
        return (
          <input
            type="text"
            value={value || ''}
            onChange={(e) => onChange(e.target.value)}
            placeholder={field.placeholder}
            required={field.isRequired}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        );

      case 'TEXTAREA':
        return (
          <textarea
            value={value || ''}
            onChange={(e) => onChange(e.target.value)}
            placeholder={field.placeholder}
            required={field.isRequired}
            rows={4}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        );

      case 'EMAIL':
        return (
          <input
            type="email"
            value={value || ''}
            onChange={(e) => onChange(e.target.value)}
            placeholder={field.placeholder}
            required={field.isRequired}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        );

      case 'NUMBER':
        return (
          <input
            type="number"
            value={value || ''}
            onChange={(e) => onChange(e.target.value)}
            placeholder={field.placeholder}
            required={field.isRequired}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        );

      case 'DATE':
        return (
          <input
            type="date"
            value={value || ''}
            onChange={(e) => onChange(e.target.value)}
            required={field.isRequired}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        );

      case 'RADIO':
        const radioOptions = field.options?.split('\n').filter(opt => opt.trim()) || [];
        return (
          <div className="mt-1 space-y-2">
            {radioOptions.map((option, index) => (
              <label key={index} className="flex items-center">
                <input
                  type="radio"
                  name={`field_${field.id}`}
                  value={option.trim()}
                  checked={value === option.trim()}
                  onChange={(e) => onChange(e.target.value)}
                  required={field.isRequired}
                  className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300"
                />
                <span className="ml-2 text-sm text-gray-900">{option.trim()}</span>
              </label>
            ))}
          </div>
        );

      case 'CHECKBOX':
        const checkboxOptions = field.options?.split('\n').filter(opt => opt.trim()) || [];
        return (
          <div className="mt-1 space-y-2">
            {checkboxOptions.map((option, index) => (
              <label key={index} className="flex items-center">
                <input
                  type="checkbox"
                  value={option.trim()}
                  checked={Array.isArray(value) ? value.includes(option.trim()) : false}
                  onChange={(e) => {
                    const currentValues = Array.isArray(value) ? value : [];
                    if (e.target.checked) {
                      onChange([...currentValues, option.trim()]);
                    } else {
                      onChange(currentValues.filter(v => v !== option.trim()));
                    }
                  }}
                  className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                />
                <span className="ml-2 text-sm text-gray-900">{option.trim()}</span>
              </label>
            ))}
          </div>
        );

      case 'DROPDOWN':
        const dropdownOptions = field.options?.split('\n').filter(opt => opt.trim()) || [];
        return (
          <select
            value={value || ''}
            onChange={(e) => onChange(e.target.value)}
            required={field.isRequired}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          >
            <option value="">Select an option</option>
            {dropdownOptions.map((option, index) => (
              <option key={index} value={option.trim()}>
                {option.trim()}
              </option>
            ))}
          </select>
        );

      case 'FILE':
        return (
          <input
            type="file"
            onChange={(e) => onChange(e.target.files?.[0])}
            required={field.isRequired}
            className="mt-1 block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-indigo-50 file:text-indigo-700 hover:file:bg-indigo-100"
          />
        );

      default:
        return (
          <input
            type="text"
            value={value || ''}
            onChange={(e) => onChange(e.target.value)}
            placeholder={field.placeholder}
            required={field.isRequired}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        );
    }
  };

  return (
    <div>
      <label className="block text-sm font-medium text-gray-700">
        {field.label}
        {field.isRequired && <span className="text-red-500 ml-1">*</span>}
      </label>
      {renderField()}
      {field.helpText && (
        <p className="mt-1 text-sm text-gray-500">{field.helpText}</p>
      )}
    </div>
  );
};

export default FormView;
