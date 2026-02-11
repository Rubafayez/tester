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

    public static final String CART_SESSION_KEY = "cart";
    public static final String CUSTOMER_SESSION_KEY = "customerId";

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/cart")
    public Cart getCart(HttpSession session) {
        return getCartFromSession(session);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItem item, HttpSession session) {
        Cart cart = getCartFromSession(session);
        cart.addItem(item);
        return ResponseEntity.ok("Item added to cart");
    }

    @PostMapping("/cart/update")
    public ResponseEntity<String> updateCart(@RequestBody CartItem item, HttpSession session) {
        Cart cart = getCartFromSession(session);
        cart.updateQuantity(item.getMovieId(), item.getQuantity());
        return ResponseEntity.ok("Cart updated");
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody CartItem item, HttpSession session) {
        Cart cart = getCartFromSession(session);
        cart.removeItem(item.getMovieId());
        return ResponseEntity.ok("Item removed from cart");
    }

    /**
     * Processes the checkout request.
     * Validates user session, cart content, and credit card information.
     * Records sales in the database and clears the cart upon success.
     *
     * @param request The checkout request containing payment details.
     * @param session The HTTP session to retrieve cart and customer ID.
     * @return ResponseEntity with success or error message.
     */
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody CheckoutRequest request, HttpSession session) {
        // 1. Verify User is Logged In
        Integer customerId = (Integer) session.getAttribute(CUSTOMER_SESSION_KEY);
        if (customerId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }

        // 2. Verify Cart is not empty
        Cart cart = getCartFromSession(session);
        if (cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        // 3. Verify Credit Card
        Optional<CreditCard> ccOpt = creditCardRepository.findById(request.getCardNumber());
        if (ccOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid credit card number");
        }
        CreditCard cc = ccOpt.get();
        if (!cc.getFirstName().equals(request.getFirstName()) ||
                !cc.getLastName().equals(request.getLastName()) ||
                !cc.getExpiration().equals(request.getExpiration())) {
            return ResponseEntity.badRequest().body("Invalid credit card information");
        }

        // 4. Record Sales
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        LocalDate now = LocalDate.now();

        for (CartItem item : cart.getItems()) {
            Optional<Movie> movieOpt = movieRepository.findById(item.getMovieId());
            if (movieOpt.isPresent()) {
                Movie movie = movieOpt.get();
                // Record a sale for each quantity? Or just one sale record per item type?
                // Usually one sale record per unit sold.
                for (int i = 0; i < item.getQuantity(); i++) {
                    Sale sale = new Sale();
                    sale.setCustomer(customer);
                    sale.setMovie(movie);
                    sale.setSaleDate(now);
                    saleRepository.save(sale);
                }
            }
        }

        // 5. Clear Cart
        cart.clear();

        return ResponseEntity.ok("Checkout successful");
    }

    private Cart getCartFromSession(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }
}
