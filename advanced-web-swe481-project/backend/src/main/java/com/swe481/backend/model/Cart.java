package com.swe481.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a shopping cart stored within the current login session.
 *
 * The cart contains a list of CartItem objects (movieId + quantity).
 * This class provides basic operations to add items, update quantities,
 * remove items, and clear the cart.
 */
public class Cart {

    /**
     * The list of items currently in the cart.
     */
    private final List<CartItem> items = new ArrayList<>();

    /**
     * @return All items currently in the cart.
     */
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Adds an item to the cart.
     * If an item with the same movieId already exists, its quantity is increased.
     *
     * @param item The CartItem to add (movieId + quantity).
     */
    public void addItem(CartItem item) {
        Optional<CartItem> existing = items.stream()
                .filter(i -> i.getMovieId().equals(item.getMovieId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
        } else {
            items.add(item);
        }
    }

    /**
     * Removes an item from the cart by movieId.
     *
     * @param movieId The movie ID of the item to remove.
     */
    public void removeItem(String movieId) {
        items.removeIf(i -> i.getMovieId().equals(movieId));
    }

    /**
     * Updates the quantity of an existing item in the cart.
     * Note: The project requirement "remove when quantity is set to 0"
     * is enforced at the controller level (where update requests are processed).
     *
     * @param movieId  The movie ID of the item to update.
     * @param quantity The new desired quantity.
     */
    public void updateQuantity(String movieId, int quantity) {
        items.stream()
                .filter(i -> i.getMovieId().equals(movieId))
                .findFirst()
                .ifPresent(i -> i.setQuantity(quantity));
    }

    /**
     * Clears all items from the cart.
     */
    public void clear() {
        items.clear();
    }
}

