# API Documentation

This document provides comprehensive API documentation for the Form Management System.

## Base URL
```
http://localhost:8080/api
```

## Authentication

All protected endpoints require a JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Endpoints

### Authentication

#### POST /auth/login
Login with username and password.

**Request Body:**
```json
{
  "username": "string",
  "password": "string"
}
```

**Response:**
```json
{
  "token": "string",
  "type": "Bearer",
  "id": 1,
  "username": "string",
  "email": "string",
  "role": "USER"
}
```

#### POST /auth/register
Register a new user.

**Request Body:**
```json
{
  "username": "string",
  "email": "string",
  "password": "string"
}
```

**Response:**
```json
"User registered successfully!"
```

### Forms

#### GET /forms/my-forms
Get all forms created by the authenticated user.

**Headers:**
- Authorization: Bearer <token>

**Response:**
```json
[
  {
    "id": 1,
    "title": "Contact Form",
    "description": "A simple contact form",
    "creatorId": 1,
    "creatorUsername": "john_doe",
    "isActive": true,
    "isPublic": true,
    "submissionLimit": null,
    "allowDuplicate": true,
    "requireLogin": false,
    "expiresAt": null,
    "createdAt": "2023-01-01T00:00:00",
    "updatedAt": "2023-01-01T00:00:00",
    "fields": [
      {
        "id": 1,
        "label": "Name",
        "fieldType": "TEXT",
        "isRequired": true,
        "fieldOrder": 1,
        "placeholder": "Enter your name",
        "helpText": "Your full name",
        "options": null,
        "validationRules": null
      }
    ],
    "responseCount": 5
  }
]
```

#### GET /forms/public
Get all public forms.

**Response:**
```json
[
  {
    "id": 1,
    "title": "Public Survey",
    "description": "A public survey form",
    "creatorId": 1,
    "creatorUsername": "john_doe",
    "isActive": true,
    "isPublic": true,
    "submissionLimit": 100,
    "allowDuplicate": false,
    "requireLogin": false,
    "expiresAt": "2023-12-31T23:59:59",
    "createdAt": "2023-01-01T00:00:00",
    "updatedAt": "2023-01-01T00:00:00",
    "fields": [],
    "responseCount": 25
  }
]
```

#### GET /forms/{id}
Get a specific form by ID.

**Headers:**
- Authorization: Bearer <token>

**Response:**
```json
{
  "id": 1,
  "title": "Contact Form",
  "description": "A simple contact form",
  "creatorId": 1,
  "creatorUsername": "john_doe",
  "isActive": true,
  "isPublic": true,
  "submissionLimit": null,
  "allowDuplicate": true,
  "requireLogin": false,
  "expiresAt": null,
  "createdAt": "2023-01-01T00:00:00",
  "updatedAt": "2023-01-01T00:00:00",
  "fields": []
}
```

#### GET /forms/public/{id}
Get a public form by ID (no authentication required).

**Response:**
```json
{
  "id": 1,
  "title": "Public Survey",
  "description": "A public survey form",
  "creatorId": 1,
  "creatorUsername": "john_doe",
  "isActive": true,
  "isPublic": true,
  "submissionLimit": 100,
  "allowDuplicate": false,
  "requireLogin": false,
  "expiresAt": "2023-12-31T23:59:59",
  "createdAt": "2023-01-01T00:00:00",
  "updatedAt": "2023-01-01T00:00:00",
  "fields": []
}
```

#### POST /forms
Create a new form.

**Headers:**
- Authorization: Bearer <token>

**Request Body:**
```json
{
  "title": "New Form",
  "description": "Form description",
  "isActive": true,
  "isPublic": true,
  "submissionLimit": 100,
  "allowDuplicate": true,
  "requireLogin": false,
  "expiresAt": "2023-12-31T23:59:59",
  "fields": [
    {
      "label": "Name",
      "fieldType": "TEXT",
      "isRequired": true,
      "fieldOrder": 1,
      "placeholder": "Enter your name",
      "helpText": "Your full name",
      "options": null,
      "validationRules": null
    }
  ]
}
```

**Response:**
```json
{
  "id": 2,
  "title": "New Form",
  "description": "Form description",
  "creatorId": 1,
  "creatorUsername": "john_doe",
  "isActive": true,
  "isPublic": true,
  "submissionLimit": 100,
  "allowDuplicate": true,
  "requireLogin": false,
  "expiresAt": "2023-12-31T23:59:59",
  "createdAt": "2023-01-01T00:00:00",
  "updatedAt": "2023-01-01T00:00:00",
  "fields": []
}
```

#### PUT /forms/{id}
Update an existing form.

**Headers:**
- Authorization: Bearer <token>

**Request Body:**
```json
{
  "title": "Updated Form",
  "description": "Updated description",
  "isActive": true,
  "isPublic": false,
  "submissionLimit": 50,
  "allowDuplicate": false,
  "requireLogin": true,
  "expiresAt": null,
  "fields": []
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Updated Form",
  "description": "Updated description",
  "creatorId": 1,
  "creatorUsername": "john_doe",
  "isActive": true,
  "isPublic": false,
  "submissionLimit": 50,
  "allowDuplicate": false,
  "requireLogin": true,
  "expiresAt": null,
  "createdAt": "2023-01-01T00:00:00",
  "updatedAt": "2023-01-01T12:00:00",
  "fields": []
}
```

#### DELETE /forms/{id}
Delete a form.

**Headers:**
- Authorization: Bearer <token>

**Response:**
```json
"Form deleted successfully"
```

### Responses

#### POST /responses/submit/{formId}
Submit a form response.

**Request Body:**
```json
{
  "1": "John Doe",
  "2": "john@example.com",
  "3": "This is a message"
}
```

**Query Parameters:**
- `respondentEmail` (optional): Email of the respondent
- `respondentName` (optional): Name of the respondent

**Response:**
```json
"Response submitted successfully"
```

#### GET /responses/form/{formId}
Get all responses for a specific form.

**Headers:**
- Authorization: Bearer <token>

**Response:**
```json
[
  {
    "id": 1,
    "formId": 1,
    "userId": null,
    "respondentEmail": "john@example.com",
    "respondentName": "John Doe",
    "ipAddress": "192.168.1.1",
    "submittedAt": "2023-01-01T12:00:00",
    "isDuplicate": false,
    "entries": [
      {
        "id": 1,
        "responseId": 1,
        "formFieldId": 1,
        "fieldValue": "John Doe"
      },
      {
        "id": 2,
        "responseId": 1,
        "formFieldId": 2,
        "fieldValue": "john@example.com"
      }
    ]
  }
]
```

#### GET /responses/form/{formId}/date-range
Get responses for a form within a date range.

**Headers:**
- Authorization: Bearer <token>

**Query Parameters:**
- `startDate`: Start date (ISO format)
- `endDate`: End date (ISO format)

**Response:**
```json
[
  {
    "id": 1,
    "formId": 1,
    "userId": null,
    "respondentEmail": "john@example.com",
    "respondentName": "John Doe",
    "ipAddress": "192.168.1.1",
    "submittedAt": "2023-01-01T12:00:00",
    "isDuplicate": false,
    "entries": []
  }
]
```

## Error Responses

### 400 Bad Request
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/forms"
}
```

### 401 Unauthorized
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "JWT token is expired",
  "path": "/api/forms"
}
```

### 403 Forbidden
```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "You don't have permission to access this resource",
  "path": "/api/forms/1"
}
```

### 404 Not Found
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Form not found",
  "path": "/api/forms/999"
}
```

### 500 Internal Server Error
```json
{
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/forms"
}
```

## Field Types

The system supports the following field types:

- `TEXT`: Single-line text input
- `TEXTAREA`: Multi-line text input
- `EMAIL`: Email input with validation
- `NUMBER`: Numeric input
- `DATE`: Date picker
- `RADIO`: Radio button group
- `CHECKBOX`: Checkbox group
- `DROPDOWN`: Select dropdown
- `FILE`: File upload

## Validation Rules

### Form Validation
- Title is required and must be between 1-200 characters
- Description is optional and limited to 1000 characters
- Submission limit must be a positive integer
- Expiration date must be in the future

### Field Validation
- Label is required and must be between 1-255 characters
- Field type must be one of the supported types
- Field order must be a non-negative integer
- Options are required for RADIO, CHECKBOX, and DROPDOWN fields

### Response Validation
- Required fields must be filled
- Email fields must be valid email addresses
- Number fields must be numeric
- Date fields must be valid dates
- File fields must be valid file types

## Rate Limiting

The API implements rate limiting to prevent abuse:

- **Authentication endpoints**: 5 requests per minute per IP
- **Form creation**: 10 requests per minute per user
- **Response submission**: 20 requests per minute per IP
- **Data retrieval**: 100 requests per minute per user

## CORS Configuration

The API supports CORS for the following origins:
- `http://localhost:3000` (development)
- `https://your-frontend-domain.com` (production)

Allowed methods:
- GET
- POST
- PUT
- DELETE
- OPTIONS

Allowed headers:
- Authorization
- Content-Type
- X-Requested-With

## Security Considerations

1. **Authentication**: All protected endpoints require valid JWT tokens
2. **Authorization**: Users can only access their own forms and responses
3. **Input Validation**: All inputs are validated and sanitized
4. **SQL Injection Prevention**: Using parameterized queries
5. **XSS Prevention**: Output encoding and CSP headers
6. **Rate Limiting**: Protection against abuse and DoS attacks

## Examples

### Complete Form Creation Flow

1. **Login**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "john_doe", "password": "password123"}'
```

2. **Create Form**
```bash
curl -X POST http://localhost:8080/api/forms \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Contact Form",
    "description": "A simple contact form",
    "isActive": true,
    "isPublic": true,
    "allowDuplicate": true,
    "requireLogin": false,
    "fields": [
      {
        "label": "Name",
        "fieldType": "TEXT",
        "isRequired": true,
        "fieldOrder": 1,
        "placeholder": "Enter your name"
      },
      {
        "label": "Email",
        "fieldType": "EMAIL",
        "isRequired": true,
        "fieldOrder": 2,
        "placeholder": "Enter your email"
      }
    ]
  }'
```

3. **Submit Response**
```bash
curl -X POST http://localhost:8080/api/responses/submit/1 \
  -H "Content-Type: application/json" \
  -d '{"1": "John Doe", "2": "john@example.com"}'
```

4. **Get Responses**
```bash
curl -X GET http://localhost:8080/api/responses/form/1 \
  -H "Authorization: Bearer <token>"
```
