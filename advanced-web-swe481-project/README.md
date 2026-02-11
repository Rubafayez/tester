# 🎬 Fabflix — Movie Rental Web Application

**Course:** SWE481 — Advanced Web Development  
**Phase:** 2 (Full Stack Implementation)

## Team Members

| Name | ID |
|---|---|
| Sadeem AlSayari | 444201182 |
| Rana AlKhuraiji | 444200455 |
| Tarfah Aldosari | 442201743 |
| Ruba Fayez Mahfoudh | 444200096 |

---

## Project Overview

Fabflix is a secure full-stack movie rental web application built on the UCI CS122B architecture. It allows authenticated users to browse, search, and rent movies from an IMDb dataset stored in PostgreSQL. The application features a premium dark-themed UI with real-time search, genre/alphabetical browsing, and a full shopping cart checkout flow.

---

## Technology Stack

| Layer | Technology |
|---|---|
| **Frontend** | Angular 17+ (Standalone Components, Lazy Loading) |
| **Backend** | Java Spring Boot 3.4 (REST API) |
| **Database** | PostgreSQL |
| **Auth** | Session-based (HTTP-only cookies) |
| **Styling** | Vanilla CSS with custom dark theme design system |

---

## Features

### 🔐 Authentication
- Login page with email/password
- Session-based authentication with Spring Security
- Auth guard protecting all routes

### 🎬 Movie Browsing
- **Top 20 Rated Movies** displayed by default
- **Search** movies by title
- **Browse by Genre** sidebar (e.g., Action, Comedy, Drama)
- **Browse by Title Letter** (A–Z, # for non-alphabetical)
- **Sorting** by Title or Rating (ascending/descending)
- **Pagination** with Next/Previous (20 per page)

### 📄 Detail Pages
- **Single Movie Page**: Title, year, director, rating, all genres, all stars (hyperlinked)
- **Single Star Page**: Name, birth year, complete filmography (hyperlinked)

### 🛒 Shopping Cart & Checkout
- **Add to Cart** from movie list and detail pages
- **Cart Page**: View items, update quantities, remove items
- **Checkout Page**: Credit card validation (matched against `creditcards` table)
- **Confirmation Page**: Order summary after successful purchase

---

## Database Schema

The PostgreSQL database `moviedb` contains these tables:

| Table | Description |
|---|---|
| `movies` | Movie records (id, title, year, director) |
| `stars` | Actor/actress records (id, name, birthYear) |
| `genres` | Genre names (id, name) |
| `ratings` | Movie ratings (movieId, rating, numVotes) |
| `stars_in_movies` | Many-to-many join (starId, movieId) |
| `genres_in_movies` | Many-to-many join (genreId, movieId) |
| `customers` | User accounts (id, name, email, password, ccId) |
| `creditcards` | Payment cards (id, firstName, lastName, expiration) |
| `sales` | Purchase transactions (id, customerId, movieId, saleDate) |

### Setup Database
```bash
# 1. Create database
psql -U postgres -c "CREATE DATABASE moviedb;"

# 2. Run schema
psql -U postgres -d moviedb -f database/schema.sql

# 3. Load data
psql -U postgres -d moviedb -f database/movie-data.sql
```

---

## Setup Instructions

### Prerequisites
- **Java 17+** (JDK, not JRE)
- **Node.js 18+** & npm
- **PostgreSQL** running on `localhost:5432`

### Backend Setup
```bash
cd backend

# Configure database credentials via Environment Variables
# You can set these in your IDE run configuration or terminal session:
# export DB_URL=jdbc:postgresql://localhost:5432/moviedb
# export DB_USER=your_postgres_username
# export DB_PASSWORD=your_postgres_password

# Build and run
./mvnw spring-boot:run
```
The API starts at `http://localhost:8080`

### Frontend Setup
```bash
cd frontend
npm install
npx ng serve
```
The app opens at `http://localhost:4200`

---

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/login` | Login with `{email, password}` |
| `POST` | `/api/auth/logout` | Logout (invalidate session) |
| `GET` | `/api/auth/status` | Check authentication status |

### Movies
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/movies` | List movies (top rated by default) |
| `GET` | `/api/movies?title=...` | Search movies by title |
| `GET` | `/api/movies?genreId=...` | Browse by genre |
| `GET` | `/api/movies?letter=...` | Browse by starting letter |
| `GET` | `/api/movies/{id}` | Single movie details |

**Query Parameters:** `page`, `size`, `sortBy` (rating/title), `sortDir` (asc/desc)

### Stars
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/stars/{id}` | Single star details with filmography |

### Genres
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/genres` | List all genres (alphabetical) |

### Shopping Cart
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/cart` | Get cart items |
| `POST` | `/api/cart` | Add movie to cart `{movieId}` |
| `PUT` | `/api/cart/{movieId}` | Update quantity `{quantity}` |
| `DELETE` | `/api/cart/{movieId}` | Remove from cart |

### Orders
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/orders/place` | Place order `{creditCardNumber, firstName, lastName}` |

---

## Project Structure

```
advanced-web-swe481-project/
├── database/
│   ├── schema.sql              # Database schema
│   └── movie-data.sql          # IMDb dataset
├── backend/                    # Spring Boot API
│   └── src/main/java/com/swe481/backend/
│       ├── entity/             # JPA Entities (7 classes)
│       ├── dto/                # Data Transfer Objects (10 classes)
│       ├── repository/         # Spring Data JPA Repositories
│       ├── service/            # Business Logic Services
│       ├── controller/         # REST Controllers
│       └── config/             # Security & CORS Config
├── frontend/                   # Angular Application
│   └── src/app/
│       ├── pages/              # Page Components (7 pages)
│       ├── services/           # HTTP Services (5 services)
│       ├── guards/             # Auth Guard
│       ├── app.routes.ts       # Lazy-loaded routing
│       └── app.config.ts       # App configuration
└── README.md
```

---

## Implementation Roadmap

| Phase | Task | Status |
|---|---|---|
| 1 | Database schema & data loading | ✅ Complete |
| 2 | Backend: JPA Entities & Repositories | ✅ Complete |
| 3 | Backend: Services & Business Logic | ✅ Complete |
| 4 | Backend: REST Controllers & Security | ✅ Complete |
| 5 | Frontend: Angular Project & Services | ✅ Complete |
| 6 | Frontend: Login & Movie List Pages | ✅ Complete |
| 7 | Frontend: Detail Pages (Movie/Star) | ✅ Complete |
| 8 | Frontend: Cart, Checkout, Confirmation | ✅ Complete |
