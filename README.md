# Phone Suggester - Modern Phone Recommendation App

A Spring Boot application that helps users find the perfect smartphone based on their preferences and requirements.

## Features

- **User Authentication**: Register and login functionality
- **Category Selection**: Browse phones by different categories
- **Phone Recommendations**: View detailed phone information with specifications
- **Search & Filter**: Search phones by brand, price range, and usage type
- **Responsive Design**: Modern UI built with React and Tailwind CSS

## Tech Stack

### Backend
- **Spring Boot 3.2.5** - Main framework
- **MongoDB** - Database
- **Spring Security** - Authentication and authorization
- **Spring Data MongoDB** - Data access layer
- **OpenAPI/Swagger** - API documentation

### Frontend
- **React** - UI framework
- **Tailwind CSS** - Styling
- **Font Awesome** - Icons

## Quick Start

### Prerequisites
- Java 21
- Maven 3.6+
- MongoDB (local or cloud)

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd phone-suggester-maven
   ```

2. **Configure MongoDB**
   - Set up MongoDB locally or use MongoDB Atlas
   - Update `application.properties` with your MongoDB connection string

3. **Build and run**
   ```bash
   mvn clean package -DskipTests
   java -jar target/phone-suggester-0.0.1-SNAPSHOT.jar
   ```

4. **Access the application**
   - Frontend: http://localhost:8080
   - API Documentation: http://localhost:8080/swagger-ui.html

## API Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/login` - User login

### Categories
- `GET /api/v1/categories` - Get all categories
- `GET /api/v1/categories/{id}` - Get category by ID
- `POST /api/v1/categories` - Create new category

### Phones
- `GET /api/v1/phones` - Get all phones (paginated)
- `GET /api/v1/phones/{id}` - Get phone by ID
- `GET /api/v1/phones/search?query={query}` - Search phones
- `GET /api/v1/phones/brand/{brand}` - Get phones by brand
- `GET /api/v1/phones/price-range?minPrice={min}&maxPrice={max}` - Get phones by price range

## Usage

1. **Register/Login**: Create an account or login with existing credentials
2. **Select Category**: Choose a phone category (Gaming, Camera, Business, etc.)
3. **Browse Phones**: View phone recommendations with detailed specifications
4. **Search & Filter**: Use search and filter options to find specific phones
5. **View Details**: Click on phones to see detailed information

## Deployment

### Backend Deployment (Render.com)
1. Create a new Web Service on Render
2. Connect your GitHub repository
3. Set build command: `mvn clean package -DskipTests`
4. Set start command: `java -jar target/phone-suggester-0.0.1-SNAPSHOT.jar`
5. Add environment variables:
   - `MONGODB_URI`: Your MongoDB connection string
   - `PORT`: 8080

### Frontend Deployment (Netlify)
1. Create a new site on Netlify
2. Connect your GitHub repository
3. Set build command: `npm run build` (if using separate frontend)
4. Set publish directory: `dist` or `build`

## Project Structure

```
src/main/java/com/example/phonesuggester/
├── controller/          # REST controllers
├── service/            # Business logic
├── repository/         # Data access layer
├── model/             # Entity models
├── dto/               # Data transfer objects
├── config/            # Configuration classes
└── exception/         # Exception handlers
```

## Contributing

This is a student project demonstrating modern web development practices with Spring Boot and React.

## License

This project is for educational purposes. 