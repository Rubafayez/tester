package com.swe481.backend.dto;

import java.util.List;

public class MovieDTO {
    private String id;
    private String title;
    private Integer year;
    private String director;
    private Float rating;
    private List<GenreDTO> genres;
    private List<StarDTO> stars;

    public MovieDTO() {
    }

    public MovieDTO(String id, String title, Integer year, String director, Float rating, List<GenreDTO> genres,
            List<StarDTO> stars) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.genres = genres;
        this.stars = stars;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }

    public List<StarDTO> getStars() {
        return stars;
    }

    public void setStars(List<StarDTO> stars) {
        this.stars = stars;
    }
}
