# Form Management System - Frontend (Vite + React)

A modern React frontend built with Vite for the Form Management System.

## 🚀 Features

- **Vite**: Lightning-fast build tool and dev server
- **React 18**: Latest React with hooks and modern patterns
- **JavaScript**: Pure JavaScript (no TypeScript)
- **Tailwind CSS**: Utility-first CSS framework
- **React Router**: Client-side routing
- **Axios**: HTTP client for API calls
- **Heroicons**: Beautiful SVG icons
- **Responsive Design**: Mobile-first approach

## 📁 Project Structure

```
src/
├── components/          # Reusable UI components
│   ├── Navbar.jsx
│   └── ProtectedRoute.jsx
├── pages/              # Page components
│   ├── Login.jsx
│   ├── Register.jsx
│   ├── Dashboard.jsx
│   ├── FormBuilder.jsx
│   ├── FormView.jsx
│   └── PublicForms.jsx
├── services/           # API service layer
│   └── api.js
├── contexts/           # React context providers
│   └── AuthContext.jsx
├── hooks/              # Custom React hooks
├── App.jsx             # Main app component
├── main.jsx            # App entry point
└── style.css           # Global styles with Tailwind
```

## 🛠️ Technology Stack

- **Vite** - Build tool and dev server
- **React 18** - UI library
- **React Router DOM** - Routing
- **Tailwind CSS** - Styling
- **Axios** - HTTP client
- **Heroicons** - Icons
- **Headless UI** - Accessible components

## 🚀 Getting Started

### Prerequisites
- Node.js (v16 or higher)
- npm or yarn

### Installation

1. **Install dependencies**
   ```bash
   npm install
   ```

2. **Start development server**
   ```bash
   npm run dev
   # or
   npm start
   ```

3. **Build for production**
   ```bash
   npm run build
   ```

4. **Preview production build**
   ```bash
   npm run preview
   ```

## 🔧 Configuration

### Vite Configuration
The project includes a `vite.config.js` with:
- React plugin for JSX support
- Development server on port 3000
- Proxy configuration for API calls to backend

### Tailwind CSS
- Configured in `tailwind.config.js`
- Includes forms plugin for better form styling
- Custom configuration for the project

## 📱 Pages

### Authentication
- **Login** (`/login`) - User login form
- **Register** (`/register`) - User registration form

### Dashboard
- **Dashboard** (`/dashboard`) - User's form management dashboard
- **Form Builder** (`/form-builder`) - Create and edit forms
- **Form Builder Edit** (`/form-builder/:formId`) - Edit existing forms

### Public Access
- **Public Forms** (`/forms/public`) - Browse public forms
- **Form View** (`/forms/:formId`) - Fill out forms

## 🔐 Authentication

The app uses JWT-based authentication with:
- Login/Register forms
- Protected routes for authenticated users
- Automatic token management
- Context-based state management

## 🎨 Styling

- **Tailwind CSS** for utility-first styling
- **Responsive design** with mobile-first approach
- **Custom components** with consistent design system
- **Form styling** with Tailwind Forms plugin

## 🔌 API Integration

The frontend communicates with the Spring Boot backend through:
- **Axios** for HTTP requests
- **Interceptors** for automatic token handling
- **Error handling** for API responses
- **Service layer** for organized API calls

## 🚀 Development

### Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm start` - Alias for dev command

### Development Server

The development server runs on `http://localhost:3000` with:
- Hot module replacement (HMR)
- Fast refresh for React components
- Proxy to backend API at `http://localhost:8080`

## 📦 Build Output

The build process creates optimized production files in the `dist/` directory:
- Minified JavaScript bundles
- Optimized CSS with Tailwind
- Static assets
- Production-ready HTML

## 🔧 Customization

### Adding New Pages
1. Create component in `src/pages/`
2. Add route in `App.jsx`
3. Update navigation in `Navbar.jsx`

### Adding New Components
1. Create component in `src/components/`
2. Export and import where needed
3. Follow existing patterns for consistency

### Styling
- Use Tailwind utility classes
- Create custom components for reusable styles
- Follow mobile-first responsive design

## 🐛 Troubleshooting

### Common Issues

1. **Port already in use**
   ```bash
   # Kill process on port 3000
   npx kill-port 3000
   ```

2. **Module not found errors**
   ```bash
   # Clear node_modules and reinstall
   rm -rf node_modules package-lock.json
   npm install
   ```

3. **Build errors**
   ```bash
   # Clear Vite cache
   rm -rf node_modules/.vite
   npm run build
   ```

## 📚 Learn More

- [Vite Documentation](https://vitejs.dev/)
- [React Documentation](https://react.dev/)
- [Tailwind CSS Documentation](https://tailwindcss.com/)
- [React Router Documentation](https://reactrouter.com/)

## 🤝 Contributing

1. Follow existing code patterns
2. Use meaningful component and variable names
3. Add comments for complex logic
4. Test components thoroughly
5. Follow responsive design principles
