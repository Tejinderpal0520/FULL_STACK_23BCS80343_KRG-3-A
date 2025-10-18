# Full Stack Form Management System

A comprehensive form management system built with React frontend and Spring Boot backend, designed for creating, managing, and analyzing dynamic forms.

## 🚀 Features

### Frontend (React + TypeScript)
- **Form Builder**: Intuitive drag-and-drop interface for creating forms
- **Dynamic Form Rendering**: Real-time form rendering for respondents
- **Creator Dashboard**: Analytics and response management
- **Authentication**: JWT-based login/registration system
- **Responsive Design**: Mobile-first design with Tailwind CSS
- **Role-based Access**: Secure routes for form creators

### Backend (Spring Boot)
- **RESTful APIs**: Complete CRUD operations for forms and responses
- **JWT Authentication**: Secure token-based authentication
- **Role-based Authorization**: User and admin roles
- **Email Integration**: Confirmation emails for form submissions
- **Data Export**: CSV/Excel export capabilities
- **Validation**: Comprehensive input validation and sanitization

### Database (MySQL)
- **User Management**: Secure user registration and authentication
- **Form Storage**: Dynamic form structure storage
- **Response Tracking**: Complete response history and analytics
- **Performance Optimized**: Indexed for fast queries

## 📁 Project Structure

```
FSProject/
├── frontend-vite/            # React JavaScript application (Vite)
│   ├── src/
│   │   ├── components/      # Reusable UI components (.jsx)
│   │   ├── pages/           # Page components (.jsx)
│   │   ├── services/        # API service layer (.js)
│   │   ├── contexts/        # React context providers (.jsx)
│   │   └── hooks/           # Custom React hooks (.js)
│   ├── public/              # Static assets
│   ├── vite.config.js       # Vite configuration
│   └── package.json         # Dependencies and scripts
├── backend/                  # Spring Boot application
│   ├── src/main/java/com/formmanagement/
│   │   ├── config/         # Security and configuration
│   │   ├── controller/     # REST API controllers
│   │   ├── service/        # Business logic layer
│   │   ├── repository/     # Data access layer
│   │   ├── model/          # JPA entities
│   │   └── dto/            # Data transfer objects
│   └── pom.xml             # Maven dependencies
├── database/                # Database scripts
│   └── init.sql            # Database initialization
└── docs/                   # Documentation
```

## 🛠️ Technology Stack

### Frontend
- **Vite** - Lightning-fast build tool
- **React 18** with JavaScript (JSX)
- **React Router** for navigation
- **Tailwind CSS** for styling
- **Axios** for API communication
- **Heroicons** for icons
- **Recharts** for analytics

### Backend
- **Spring Boot 3.2.0**
- **Spring Security** with JWT
- **Spring Data JPA** for database operations
- **MySQL** database
- **Maven** for dependency management
- **Jackson** for JSON processing

## 🚀 Getting Started

### Prerequisites
- **Node.js** (v16 or higher)
- **Java** (v17 or higher)
- **MySQL** (v8.0 or higher)
- **Maven** (v3.6 or higher)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd FSProject
   ```

2. **Setup Database**
   ```bash
   # Create MySQL database
   mysql -u root -p < database/init.sql
   ```

3. **Setup Backend**
   ```bash
   cd backend
   mvn clean install
   ```

4. **Setup Frontend**
   ```bash
   cd frontend
   npm install
   ```

### Running the Application

1. **Start MySQL Database**
   ```bash
   # Make sure MySQL is running on localhost:3306
   ```

2. **Run Spring Boot Backend**
   ```bash
   cd backend
   mvn spring-boot:run
   # Backend will be available at http://localhost:8080
   ```

3. **Run React Frontend (Vite)**
   ```bash
   cd frontend-vite
   npm run dev
   # Frontend will be available at http://localhost:3000
   ```

## 📚 API Documentation

### Authentication Endpoints
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Form Management
- `GET /api/forms/my-forms` - Get user's forms
- `GET /api/forms/public` - Get public forms
- `GET /api/forms/{id}` - Get form by ID
- `POST /api/forms` - Create new form
- `PUT /api/forms/{id}` - Update form
- `DELETE /api/forms/{id}` - Delete form

### Response Management
- `POST /api/responses/submit/{formId}` - Submit form response
- `GET /api/responses/form/{formId}` - Get form responses
- `GET /api/responses/form/{formId}/date-range` - Get responses by date range

## 🎯 Usage Guide

### For Form Creators
1. **Register/Login** to access the dashboard
2. **Create Forms** using the form builder
3. **Configure Settings** like public/private access, submission limits
4. **View Responses** and analytics in the dashboard
5. **Export Data** to CSV/Excel format

### For Form Respondents
1. **Browse Public Forms** on the public forms page
2. **Fill Out Forms** with real-time validation
3. **Submit Responses** with confirmation
4. **View Submission Status** and confirmation messages

## 🔧 Configuration

### Backend Configuration (`application.yml`)
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/form_management
    username: root
    password: your_password
  
  mail:
    host: smtp.gmail.com
    username: your-email@gmail.com
    password: your-app-password

jwt:
  secret: your-secret-key
  expiration: 86400000
```

### Frontend Configuration
Update API base URL in `src/services/api.ts` if needed:
```typescript
const API_BASE_URL = 'http://localhost:8080/api';
```

## 🧪 Testing

### Backend Testing
```bash
cd backend
mvn test
```

### Frontend Testing
```bash
cd frontend
npm test
```

## 📊 Features in Detail

### Form Builder
- **Dynamic Field Types**: Text, Email, Number, Date, Radio, Checkbox, Dropdown, File
- **Field Validation**: Required fields, custom validation rules
- **Form Settings**: Public/private access, submission limits, duplicate prevention
- **Real-time Preview**: Live form preview while building

### Response Management
- **Response Tracking**: Complete submission history
- **Analytics Dashboard**: Response counts, submission trends
- **Data Export**: CSV/Excel export with filtering
- **Email Notifications**: Automatic confirmation emails

### Security Features
- **JWT Authentication**: Secure token-based authentication
- **Role-based Access**: Creator vs respondent permissions
- **Input Validation**: XSS and injection attack prevention
- **CORS Configuration**: Secure cross-origin requests

## 🚀 Deployment

### Backend Deployment
1. Build the JAR file: `mvn clean package`
2. Deploy to your preferred cloud platform (AWS, Heroku, etc.)
3. Configure environment variables for database and email

### Frontend Deployment
1. Build the production bundle: `npm run build`
2. Deploy to static hosting (Netlify, Vercel, etc.)
3. Update API base URL for production

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Check the documentation in the `docs/` folder
- Review the API documentation at `/api/docs` when running

## 🔮 Future Enhancements

- [ ] Drag-and-drop form builder interface
- [ ] Advanced analytics and reporting
- [ ] Form templates and themes
- [ ] Multi-language support
- [ ] Advanced user permissions
- [ ] Form versioning and history
- [ ] Integration with external services
- [ ] Mobile app development
