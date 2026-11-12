# SpringEcom – Full Stack E-Commerce Application

SpringEcom is a full-stack E-Commerce application developed using **Java, Spring Boot, and React**. The application is designed to provide a structured platform for managing products, handling product images, and processing customer orders through RESTful APIs.

The backend is built using Spring Boot and follows a layered architecture, separating responsibilities across Controller, Service, and Repository layers. This makes the application modular, maintainable, and easier to extend with additional features.

The application provides product management functionalities, including creating new products, retrieving product details, updating existing products, and deleting products. It also supports product image uploading and retrieval, allowing images to be associated with individual products.

Order management functionality enables the application to handle customer orders and maintain order-related information.

## Key Features

* **Product Management:** Create, retrieve, update, and delete products.
* **Image Management:** Upload, store, and retrieve product images.
* **Order Management:** Handle customer orders and order processing.
* **RESTful APIs:** Expose backend functionalities through REST endpoints.
* **Layered Architecture:** Organized Controller, Service, and Repository layers.
* **Database Integration:** Persist application data using PostgreSQL and Spring Data JPA.
* **Frontend Integration:** Connect the React frontend with Spring Boot backend APIs.
* **Modular Design:** Maintain separation of concerns for easier development and future enhancements.

## Technologies Used

| Category             | Technologies                |
| -------------------- | --------------------------- |
| Programming Language | Java                        |
| Backend Framework    | Spring Boot                 |
| Frontend             | React, JavaScript           |
| Build Tool           | Maven                       |
| Database             | PostgreSQL                  |
| ORM                  | Hibernate / Spring Data JPA |
| API Development      | REST APIs                   |
| Frontend Build Tool  | Vite                        |
| API Testing          | Postman                     |
| Version Control      | Git & GitHub                |

## Project Structure

```text
SpringEcom/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/telusko/SpringEcom/
│       │       ├── controller/
│       │       ├── service/
│       │       ├── repo/
│       │       └── model/
│       │
│       └── resources/
│           └── application.properties
│
├── ecom-frontend-5/
│   ├── public/
│   ├── src/
│   ├── package.json
│   └── vite.config.js
│
├── pom.xml
└── README.md
```

## Running the Application

### Backend Setup

1. Clone the repository.
2. Open the backend project in IntelliJ IDEA.
3. Configure your PostgreSQL database connection.
4. Run the Spring Boot application.

### Frontend Setup

1. Navigate to the frontend directory.
2. Install dependencies.
3. Start the development server.

```bash
cd ecom-frontend-5
npm install
npm run dev
```

Open the local URL provided by Vite in your browser.

## Future Enhancements

* User authentication and authorization.
* Shopping cart functionality.
* Payment gateway integration.
* Product search and filtering.
* AI-powered product recommendations.

## Project Status

**Currently in Development** 🚧
