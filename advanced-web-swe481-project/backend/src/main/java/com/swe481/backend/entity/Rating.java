package com.swe481.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @Column(name = "movieid", length = 10)
    private String movieId;

    @Column(nullable = false)
    private Float rating;

    @Column(name = "numvotes", nullable = false)
    private Integer numVotes;

    @OneToOne
    @MapsId
    @JoinColumn(name = "movieid")
    private Movie movie;

    public Rating() {
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(Integer numVotes) {
        this.numVotes = numVotes;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
