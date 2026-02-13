package com.swe481.backend.controller;

import com.swe481.backend.dto.CheckoutRequest;
import com.swe481.backend.entity.CreditCard;
import com.swe481.backend.entity.Customer;
import com.swe481.backend.entity.Movie;
import com.swe481.backend.entity.Sale;
import com.swe481.backend.model.Cart;
import com.swe481.backend.model.CartItem;
import com.swe481.backend.repository.CreditCardRepository;
import com.swe481.backend.repository.CustomerRepository;
import com.swe481.backend.repository.MovieRepository;
import com.swe481.backend.repository.SaleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CartController {

    /**
     * Session key used to store/retrieve the user's shopping cart.
     * The cart is scoped to the current login session.
     */
    public static final String CART_SESSION_KEY = "cart";

    /**
     * Session key used to store/retrieve the logged-in customer's ID.
     * This is used to ensure checkout operations are performed only by authenticated users.
     */
    public static final String CUSTOMER_SESSION_KEY = "customerId";

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Retrieves the shopping cart for the current session.
     *
     * Endpoint: GET /api/cart
     *
     * @param session HTTP session used to persist the cart within the current login session.
     * @return The current session cart. If none exists, a new cart will be created and returned.
     */
    @GetMapping("/cart")
    public Cart getCart(HttpSession session) {
        return getCartFromSession(session);
    }

    /**
     * Adds a movie to the shopping cart for the current session.
     * If the movie already exists in the cart, its quantity should be increased by the amount provided.
     *
     * Endpoint: POST /api/cart/add
     *
     * @param item    CartItem containing the movie ID and quantity to add.
     * @param session HTTP session used to persist the cart within the current login session.
     * @return ResponseEntity containing a simple success message.
     */
    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItem item, HttpSession session) {
        Cart cart = getCartFromSession(session);
        cart.addItem(item);
        return ResponseEntity.ok("Item added to cart");
    }

    /**
     * Updates the quantity of a movie in the shopping cart for the current session.
     *
     * Requirement:
     * - The shopping cart must show the quantity of each movie in the cart (within the current login session).
     * - Customers must be able to modify the quantity of each movie.
     * - A movie must be removed from the shopping cart when its quantity is set to 0.
     *
     * Endpoint: POST /api/cart/update
     *
     * @param item    CartItem containing the movie ID and the new desired quantity.
     * @param session HTTP session used to persist the cart within the current login session.
     * @return ResponseEntity containing a simple success message.
     */
    @PostMapping("/cart/update")
    public ResponseEntity<String> updateCart(@RequestBody CartItem item, HttpSession session) {
        Cart cart = getCartFromSession(session);

        // If quantity is set to 0, remove the movie from the cart (per project requirement).
        if (item.getQuantity() == 0) {
            cart.removeItem(item.getMovieId());
        } else {
            cart.updateQuantity(item.getMovieId(), item.getQuantity());
        }

        return ResponseEntity.ok("Cart updated");
    }

    /**
     * Processes the checkout request.
     *
     * Checkout requirements:
     * 1) The user must be logged in (customerId must exist in the session).
     * 2) The cart must not be empty.
     * 3) The transaction succeeds only if customers provide correct payment information
     *    that matches a record in the credit cards table (NOT customers table).
     * 4) If the transaction succeeds, it must be recorded in the system (sales table)
     *    and a confirmation page should be displayed by the frontend.
     *
     * Endpoint: POST /api/checkout
     *
     * @param request CheckoutRequest containing payment details:
     *                firstName, lastName, cardNumber, expiration.
     * @param session HTTP session used to retrieve cart and customerId.
     * @return ResponseEntity with a success message (200),
     *         unauthorized message (401), or bad request message (400).
     */
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody CheckoutRequest request, HttpSession session) {

        // 1) Verify user is logged in
        Integer customerId = (Integer) session.getAttribute(CUSTOMER_SESSION_KEY);
        if (customerId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }

        // 2) Verify cart is not empty
        Cart cart = getCartFromSession(session);
        if (cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        // 3) Verify credit card information against the credit cards table (NOT customers table)
        Optional<CreditCard> ccOpt = creditCardRepository.findById(request.getCardNumber());
        if (ccOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid credit card number");
        }

        CreditCard cc = ccOpt.get();
        boolean matchesCardInfo =
                cc.getFirstName().equals(request.getFirstName()) &&
                cc.getLastName().equals(request.getLastName()) &&
                cc.getExpiration().equals(request.getExpiration());

        if (!matchesCardInfo) {
            return ResponseEntity.badRequest().body("Invalid credit card information");
        }

        // 4) Record sales for each unit in the cart
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        LocalDate now = LocalDate.now();

        for (CartItem item : cart.getItems()) {
            Optional<Movie> movieOpt = movieRepository.findById(item.getMovieId());
            if (movieOpt.isPresent()) {
                Movie movie = movieOpt.get();

                // Record one sale per unit (quantity)
                for (int i = 0; i < item.getQuantity(); i++) {
                    Sale sale = new Sale();
                    sale.setCustomer(customer);
                    sale.setMovie(movie);
                    sale.setSaleDate(now);
                    saleRepository.save(sale);
                }
            }
        }

        // 5) Clear cart upon successful checkout
        cart.clear();

        return ResponseEntity.ok("Checkout successful");
    }

    /**
     * Retrieves the Cart object from the HTTP session. If no cart exists,
     * a new cart is created, stored in session, and returned.
     *
     * @param session HTTP session for the current user.
     * @return The cart associated with the current session.
     */
    private Cart getCartFromSession(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }
}

