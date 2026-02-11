package com.swe481.backend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @Column(length = 10)
    private String id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, length = 100)
    private String director;

    @ManyToMany
    @JoinTable(name = "stars_in_movies", joinColumns = @JoinColumn(name = "movieid"), inverseJoinColumns = @JoinColumn(name = "starid"))
    private Set<Star> stars = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "genres_in_movies", joinColumns = @JoinColumn(name = "movieid"), inverseJoinColumns = @JoinColumn(name = "genreid"))
    private Set<Genre> genres = new HashSet<>();

    @OneToOne(mappedBy = "movie", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Rating rating;

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Set<Star> getStars() {
        return stars;
    }

    public void setStars(Set<Star> stars) {
        this.stars = stars;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
