package com.swe481.backend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stars")
public class Star {

    @Id
    @Column(length = 10)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "birthyear")
    private Integer birthYear;

    @ManyToMany(mappedBy = "stars")
    private Set<Movie> movies = new HashSet<>();

    public Star() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
