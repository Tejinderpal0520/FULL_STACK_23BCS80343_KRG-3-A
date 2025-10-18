# Deployment Guide

This guide will help you deploy the Full Stack Form Management System to production.

## Prerequisites

- **Java 17+** installed
- **Node.js 16+** installed
- **MySQL 8.0+** database
- **Maven 3.6+** for backend
- **npm/yarn** for frontend

## Database Setup

### 1. Create Database
```sql
-- Run the initialization script
mysql -u root -p < database/init.sql
```

### 2. Configure Database Connection
Update `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://your-db-host:3306/form_management
    username: your-db-username
    password: your-db-password
```

## Backend Deployment

### 1. Build the Application
```bash
cd backend
mvn clean package -DskipTests
```

### 2. Configure Environment Variables
Create a `.env` file or set environment variables:

```bash
export JWT_SECRET=your-super-secret-jwt-key
export MAIL_USERNAME=your-email@gmail.com
export MAIL_PASSWORD=your-app-password
export DB_URL=jdbc:mysql://your-db-host:3306/form_management
export DB_USERNAME=your-db-username
export DB_PASSWORD=your-db-password
```

### 3. Run the Application
```bash
# Option 1: Using Maven
mvn spring-boot:run

# Option 2: Using JAR file
java -jar target/form-management-backend-0.0.1-SNAPSHOT.jar
```

### 4. Production Configuration
For production, update `application.yml`:

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  profiles:
    active: production
  
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000

cors:
  allowed-origins: https://your-frontend-domain.com
```

## Frontend Deployment

### 1. Install Dependencies
```bash
cd frontend
npm install
```

### 2. Update API Configuration
Update `src/services/api.ts`:

```typescript
const API_BASE_URL = 'https://your-backend-domain.com/api';
```

### 3. Build for Production
```bash
npm run build
```

### 4. Deploy Static Files
The `build` folder contains the production-ready files. Deploy these to your static hosting service:

- **Netlify**: Drag and drop the `build` folder
- **Vercel**: Connect your repository and set build command to `npm run build`
- **AWS S3**: Upload the `build` folder contents
- **Nginx**: Copy files to your web server directory

## Docker Deployment

### 1. Backend Dockerfile
Create `backend/Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/form-management-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 2. Frontend Dockerfile
Create `frontend/Dockerfile`:

```dockerfile
FROM node:16-alpine as build

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### 3. Docker Compose
Create `docker-compose.yml`:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: form_management
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      - DB_URL=jdbc:mysql://mysql:3306/form_management
      - DB_USERNAME=root
      - DB_PASSWORD=rootpassword
      - JWT_SECRET=your-secret-key
    depends_on:
      - mysql

  frontend:
    build: ./frontend
    ports:
      - "80:80"
    depends_on:
      - backend

volumes:
  mysql_data:
```

### 4. Run with Docker Compose
```bash
docker-compose up -d
```

## Cloud Deployment

### AWS Deployment

#### 1. Backend (AWS Elastic Beanstalk)
```bash
# Create deployment package
cd backend
mvn clean package
zip -r form-management-backend.zip target/form-management-backend-0.0.1-SNAPSHOT.jar

# Upload to Elastic Beanstalk
```

#### 2. Frontend (AWS S3 + CloudFront)
```bash
# Build and upload to S3
cd frontend
npm run build
aws s3 sync build/ s3://your-bucket-name
```

#### 3. Database (AWS RDS)
- Create MySQL RDS instance
- Update connection string in backend configuration

### Heroku Deployment

#### 1. Backend
Create `backend/Procfile`:
```
web: java -jar target/form-management-backend-0.0.1-SNAPSHOT.jar
```

```bash
# Deploy to Heroku
cd backend
heroku create your-app-name
git push heroku main
```

#### 2. Frontend
```bash
# Deploy to Heroku
cd frontend
heroku create your-frontend-name
git push heroku main
```

## Environment Variables

### Backend Environment Variables
```bash
# Database
DB_URL=jdbc:mysql://host:port/database
DB_USERNAME=username
DB_PASSWORD=password

# JWT
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000

# Email
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

# CORS
CORS_ALLOWED_ORIGINS=https://your-frontend-domain.com
```

### Frontend Environment Variables
```bash
# API Configuration
REACT_APP_API_URL=https://your-backend-domain.com/api
```

## Security Considerations

### 1. Database Security
- Use strong passwords
- Enable SSL connections
- Restrict database access
- Regular backups

### 2. Application Security
- Use HTTPS in production
- Set secure JWT secrets
- Configure CORS properly
- Enable rate limiting
- Input validation and sanitization

### 3. Environment Security
- Never commit secrets to version control
- Use environment variables for sensitive data
- Regular security updates
- Monitor application logs

## Monitoring and Logging

### 1. Application Monitoring
- Set up health checks
- Monitor response times
- Track error rates
- Database performance monitoring

### 2. Logging
- Configure log levels
- Centralized logging (ELK stack)
- Error tracking (Sentry)
- Performance monitoring

## Backup Strategy

### 1. Database Backups
```bash
# Daily automated backups
mysqldump -u username -p form_management > backup_$(date +%Y%m%d).sql
```

### 2. Application Backups
- Source code in version control
- Configuration files backup
- Environment variables backup

## Troubleshooting

### Common Issues

1. **Database Connection Issues**
   - Check connection string
   - Verify database credentials
   - Ensure database is running

2. **CORS Issues**
   - Update allowed origins
   - Check frontend URL configuration

3. **JWT Token Issues**
   - Verify JWT secret
   - Check token expiration
   - Validate token format

4. **Email Issues**
   - Check SMTP configuration
   - Verify email credentials
   - Test email sending

### Logs to Check
- Application logs
- Database logs
- Web server logs
- Browser console logs

## Performance Optimization

### 1. Database Optimization
- Add proper indexes
- Query optimization
- Connection pooling

### 2. Application Optimization
- Enable caching
- Optimize queries
- Use CDN for static assets
- Compress responses

### 3. Frontend Optimization
- Code splitting
- Lazy loading
- Image optimization
- Bundle size optimization
