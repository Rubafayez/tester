package com.swe481.backend.model;

import java.util.Objects;

public class CartItem {
    private String movieId;
    private String movieTitle;
    private int quantity;

    public CartItem() {
    }

    public CartItem(String movieId, String movieTitle, int quantity) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.quantity = quantity;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(movieId, cartItem.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }
}
