package com.swe481.backend.specification;

import com.swe481.backend.entity.Genre;
import com.swe481.backend.entity.Movie;
import com.swe481.backend.entity.Star;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;

public class MovieSpecifications {

    public static Specification<Movie> hasTitle(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Movie> hasYear(Integer year) {
        return (root, query, cb) -> {
            if (year == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("year"), year);
        };
    }

    public static Specification<Movie> hasDirector(String director) {
        return (root, query, cb) -> {
            if (director == null || director.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("director")), "%" + director.toLowerCase() + "%");
        };
    }

    public static Specification<Movie> hasStarName(String starName) {
        return (root, query, cb) -> {
            if (starName == null || starName.isEmpty()) {
                return cb.conjunction();
            }
            Join<Movie, Star> stars = root.join("stars");
            return cb.like(cb.lower(stars.get("name")), "%" + starName.toLowerCase() + "%");
        };
    }

    public static Specification<Movie> hasGenreName(String genreName) {
        return (root, query, cb) -> {
            if (genreName == null || genreName.isEmpty()) {
                return cb.conjunction();
            }
            Join<Movie, Genre> genres = root.join("genres");
            return cb.equal(cb.lower(genres.get("name")), genreName.toLowerCase());
        };
    }

    public static Specification<Movie> titleStartsWith(String charStart) {
        return (root, query, cb) -> {
            if (charStart == null || charStart.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("title")), charStart.toLowerCase() + "%");
        };
    }
}
