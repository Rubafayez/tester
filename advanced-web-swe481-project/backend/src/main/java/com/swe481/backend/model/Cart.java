package com.swe481.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

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

    public void removeItem(String movieId) {
        items.removeIf(i -> i.getMovieId().equals(movieId));
    }

    public void updateQuantity(String movieId, int quantity) {
        items.stream()
                .filter(i -> i.getMovieId().equals(movieId))
                .findFirst()
                .ifPresent(i -> i.setQuantity(quantity));
    }

    public void clear() {
        items.clear();
    }
}
