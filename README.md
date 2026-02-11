API & Interface Specification Requirement:

By the end of this phase, teams must fully specify the interfaces of the system
before implementation. This includes:

■ REST API endpoints (URLs, HTTP methods).

■ Request parameters and payloads.

■ Response formats and HTTP status codes.

■ Authentication requirements (if applicable).



API & Interface Specification
1. Authentication
Authentication Requirements:

Currently, endpoints under /api/** are open (permitted all) for development convenience as per 
SecurityConfig.java

AuthController
 establishes a session-based authentication by storing customerId in the HttpSession.
 
CartController
 endpoints (checkout) explicitly check for customerId in the session.
 
Login
URL: /api/login
Method: POST
Payload:
json
{
  "email": "user@example.com",
  "password": "password123"
}
Response:
Success (200 OK):
json
{
  "status": "success",
  "message": "Login successful",
  "customerId": 123
}
Failure (401 Unauthorized):
json
{
  "status": "fail",
  "message": "Invalid email or password",
  "customerId": null
}

2. Movies
Search & Browse Movies
URL: /api/movies
Method: GET
Parameters (Query Strings):
title
 (optional): Filter by movie title.
year (optional): Filter by release year.
director (optional): Filter by director name.
star (optional): Filter by star's name.
genre (optional): Filter by genre name.
charStart (optional): Filter by title starting with a character.
page (default: 0): Page number.
limit (default: 20): Items per page.
sort (default: "title"): Sort field.
order (default: "asc"): Sort order ("asc" or "desc").
Response (200 OK):
Returns a standard Spring Data Page<MovieDTO> object.
json
{
  "content": [
    {
      "id": "tt1234567",
      "title": "Movie Title",
      "year": 2023,
      "director": "Director Name",
      "rating": 8.5,
      "genres": [{"id": 1, "name": "Action"}],
      "stars": [{"id": "nm123", "name": "Star Name", "birthYear": 1980}]
    }
  ],
  "totalPages": 5,
  "totalElements": 100,
  "last": false,
  "size": 20,
  "number": 0,
  ...
}
Get Movie Details
URL: /api/movies/{id}
Method: GET
Parameters: id (Path Variable, e.g., tt0092099)
Response (200 OK):
json
{
  "id": "tt0092099",
  "title": "Top Gun",
  "year": 1986,
  "director": "Tony Scott",
  "rating": 6.9,
  "genres": [...],
  "stars": [...]
}

3. Stars
Get Star Details
URL: /api/stars/{id}
Method: GET
Parameters: id (Path Variable)
Response (200 OK):
json
{
  "id": "nm0000123",
  "name": "Tom Cruise",
  "birthYear": 1962,
  "movies": [
    { "id": "tt0092099", "title": "Top Gun", "year": 1986, "director": "Tony Scott" }
  ]
}

4. Shopping Cart & Checkout
Get Cart
URL: /api/cart
Method: GET
Response (200 OK):
json
{
  "items": [
    {
      "movieId": "tt1234567",
      "movieTitle": "Movie Name",
      "quantity": 2
    }
  ]
}

Add to Cart
URL: /api/cart/add
Method: POST
Payload:
json
{
  "movieId": "tt1234567",
  "movieTitle": "Movie Name",
  "quantity": 1
}
Response: 200 OK ("Item added to cart")

Update Cart Quantity
URL: /api/cart/update
Method: POST
Payload:
json
{
  "movieId": "tt1234567",
  "quantity": 5
}
Response: 200 OK ("Cart updated")

Remove from Cart
URL: /api/cart/remove
Method: POST
Payload:
json
{
  "movieId": "tt1234567"
}
Response: 200 OK ("Item removed from cart")

Checkout
URL: /api/checkout
Method: POST
Requirements: User must be logged in (session customerId present).
Payload:
json
{
  "firstName": "John",
  "lastName": "Doe",
  "cardNumber": "1234567890",
  "expiration": "2025-12-31"
}

Response:
Success (200 OK): "Checkout successful"
Failure (400 Bad Request): "Cart is empty", "Invalid credit card number", or "Invalid credit card information".
Failure (401 Unauthorized): "User not logged in".
