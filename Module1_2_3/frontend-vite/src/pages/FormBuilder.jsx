import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { formService } from '../services/api';
import { PlusIcon, TrashIcon } from '@heroicons/react/24/outline';

const FormBuilder = () => {
  const { formId } = useParams();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    title: '',
    description: '',
    isActive: true,
    isPublic: true,
    allowDuplicate: true,
    requireLogin: false,
    fields: [],
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (formId) {
      fetchForm();
    }
  }, [formId]);

  const fetchForm = async () => {
    try {
      const data = await formService.getFormById(parseInt(formId));
      setForm(data);
    } catch (err) {
      setError(err.response?.data || 'Failed to fetch form');
    }
  };

  const handleInputChange = (e) => {
    const { name, value, type } = e.target;
    setForm(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? e.target.checked : value,
    }));
  };

  const addField = () => {
    const newField = {
      label: '',
      fieldType: 'TEXT',
      isRequired: false,
      fieldOrder: form.fields?.length || 0,
      placeholder: '',
      helpText: '',
      options: '',
    };
    setForm(prev => ({
      ...prev,
      fields: [...(prev.fields || []), newField],
    }));
  };

  const updateField = (index, field) => {
    setForm(prev => ({
      ...prev,
      fields: prev.fields?.map((f, i) => i === index ? field : f) || [],
    }));
  };

  const removeField = (index) => {
    setForm(prev => ({
      ...prev,
      fields: prev.fields?.filter((_, i) => i !== index) || [],
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      if (formId) {
        await formService.updateForm(parseInt(formId), form);
      } else {
        await formService.createForm(form);
      }
      navigate('/dashboard');
    } catch (err) {
      setError(err.response?.data || 'Failed to save form');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">
          {formId ? 'Edit Form' : 'Create New Form'}
        </h1>
      </div>

      {error && (
        <div className="mb-4 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-8">
        {/* Form Basic Information */}
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-lg font-medium text-gray-900 mb-4">Form Information</h2>
          
          <div className="grid grid-cols-1 gap-6">
            <div>
              <label htmlFor="title" className="block text-sm font-medium text-gray-700">
                Form Title *
              </label>
              <input
                type="text"
                name="title"
                id="title"
                required
                value={form.title}
                onChange={handleInputChange}
                className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>

            <div>
              <label htmlFor="description" className="block text-sm font-medium text-gray-700">
                Description
              </label>
              <textarea
                name="description"
                id="description"
                rows={3}
                value={form.description}
                onChange={handleInputChange}
                className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="flex items-center">
                <input
                  type="checkbox"
                  name="isPublic"
                  id="isPublic"
                  checked={form.isPublic}
                  onChange={handleInputChange}
                  className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                />
                <label htmlFor="isPublic" className="ml-2 block text-sm text-gray-900">
                  Public form (visible to everyone)
                </label>
              </div>

              <div className="flex items-center">
                <input
                  type="checkbox"
                  name="requireLogin"
                  id="requireLogin"
                  checked={form.requireLogin}
                  onChange={handleInputChange}
                  className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                />
                <label htmlFor="requireLogin" className="ml-2 block text-sm text-gray-900">
                  Require login to submit
                </label>
              </div>

              <div className="flex items-center">
                <input
                  type="checkbox"
                  name="allowDuplicate"
                  id="allowDuplicate"
                  checked={form.allowDuplicate}
                  onChange={handleInputChange}
                  className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                />
                <label htmlFor="allowDuplicate" className="ml-2 block text-sm text-gray-900">
                  Allow duplicate submissions
                </label>
              </div>
            </div>
          </div>
        </div>

        {/* Form Fields */}
        <div className="bg-white shadow rounded-lg p-6">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-lg font-medium text-gray-900">Form Fields</h2>
            <button
              type="button"
              onClick={addField}
              className="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700"
            >
              <PlusIcon className="h-4 w-4 mr-2" />
              Add Field
            </button>
          </div>

          {form.fields?.length === 0 ? (
            <div className="text-center py-8 text-gray-500">
              No fields added yet. Click "Add Field" to get started.
            </div>
          ) : (
            <div className="space-y-4">
              {form.fields?.map((field, index) => (
                <FieldEditor
                  key={index}
                  field={field}
                  index={index}
                  onUpdate={(updatedField) => updateField(index, updatedField)}
                  onRemove={() => removeField(index)}
                />
              ))}
            </div>
          )}
        </div>

        {/* Submit Button */}
        <div className="flex justify-end space-x-4">
          <button
            type="button"
            onClick={() => navigate('/dashboard')}
            className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
          >
            Cancel
          </button>
          <button
            type="submit"
            disabled={loading}
            className="px-4 py-2 border border-transparent rounded-md text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 disabled:opacity-50"
          >
            {loading ? 'Saving...' : 'Save Form'}
          </button>
        </div>
      </form>
    </div>
  );
};

const FieldEditor = ({ field, index, onUpdate, onRemove }) => {
  const handleChange = (e) => {
    const { name, value, type } = e.target;
    onUpdate({
      ...field,
      [name]: type === 'checkbox' ? e.target.checked : value,
    });
  };

  return (
    <div className="border border-gray-200 rounded-lg p-4">
      <div className="flex justify-between items-center mb-4">
        <h3 className="text-sm font-medium text-gray-900">Field {index + 1}</h3>
        <button
          type="button"
          onClick={onRemove}
          className="text-red-600 hover:text-red-900"
        >
          <TrashIcon className="h-4 w-4" />
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Label *</label>
          <input
            type="text"
            name="label"
            value={field.label}
            onChange={handleChange}
            required
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Field Type *</label>
          <select
            name="fieldType"
            value={field.fieldType}
            onChange={handleChange}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          >
            <option value="TEXT">Text</option>
            <option value="TEXTAREA">Textarea</option>
            <option value="EMAIL">Email</option>
            <option value="NUMBER">Number</option>
            <option value="DATE">Date</option>
            <option value="RADIO">Radio</option>
            <option value="CHECKBOX">Checkbox</option>
            <option value="DROPDOWN">Dropdown</option>
            <option value="FILE">File</option>
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Placeholder</label>
          <input
            type="text"
            name="placeholder"
            value={field.placeholder}
            onChange={handleChange}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Help Text</label>
          <input
            type="text"
            name="helpText"
            value={field.helpText}
            onChange={handleChange}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        </div>

        {(field.fieldType === 'RADIO' || field.fieldType === 'CHECKBOX' || field.fieldType === 'DROPDOWN') && (
          <div className="md:col-span-2">
            <label className="block text-sm font-medium text-gray-700">Options (one per line)</label>
            <textarea
              name="options"
              value={field.options}
              onChange={handleChange}
              rows={3}
              className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              placeholder="Option 1&#10;Option 2&#10;Option 3"
            />
          </div>
        )}

        <div className="md:col-span-2">
          <div className="flex items-center">
            <input
              type="checkbox"
              name="isRequired"
              checked={field.isRequired}
              onChange={handleChange}
              className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
            />
            <label className="ml-2 block text-sm text-gray-900">
              Required field
            </label>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FormBuilder;
