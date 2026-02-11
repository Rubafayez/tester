package com.swe481.backend.controller;

import com.swe481.backend.dto.GenreDTO;
import com.swe481.backend.dto.MovieDTO;
import com.swe481.backend.dto.StarDTO;
import com.swe481.backend.entity.Movie;
import com.swe481.backend.repository.MovieRepository;
import com.swe481.backend.specification.MovieSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

        @Autowired
        private MovieRepository movieRepository;

        @GetMapping
        public Page<MovieDTO> getMovies(
                        @RequestParam(name = "title", required = false) String title,
                        @RequestParam(name = "year", required = false) Integer year,
                        @RequestParam(name = "director", required = false) String director,
                        @RequestParam(name = "star", required = false) String star,
                        @RequestParam(name = "genre", required = false) String genre,
                        @RequestParam(name = "charStart", required = false) String charStart,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "limit", defaultValue = "20") int limit,
                        @RequestParam(name = "sort", defaultValue = "title") String sort,
                        @RequestParam(name = "order", defaultValue = "asc") String order) {
                Specification<Movie> spec = Specification.where(MovieSpecifications.hasTitle(title))
                                .and(MovieSpecifications.hasYear(year))
                                .and(MovieSpecifications.hasDirector(director))
                                .and(MovieSpecifications.hasStarName(star))
                                .and(MovieSpecifications.hasGenreName(genre))
                                .and(MovieSpecifications.titleStartsWith(charStart));

                Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sort));

                Page<Movie> moviePage = movieRepository.findAll(spec, pageable);

                return moviePage.map(this::convertToDTO);
        }

        @GetMapping("/{id}")
        public MovieDTO getMovieById(@PathVariable("id") String id) {
                return movieRepository.findById(id)
                                .map(this::convertToDTO)
                                .orElse(null);
        }

        private MovieDTO convertToDTO(Movie movie) {
                List<GenreDTO> genreDTOs = movie.getGenres().stream()
                                .map(g -> new GenreDTO(g.getId(), g.getName()))
                                .collect(Collectors.toList());

                List<StarDTO> starDTOs = movie.getStars().stream()
                                .map(s -> new StarDTO(s.getId(), s.getName(), s.getBirthYear()))
                                .collect(Collectors.toList());

                Float rating = movie.getRating() != null ? movie.getRating().getRating() : null;

                return new MovieDTO(
                                movie.getId(),
                                movie.getTitle(),
                                movie.getYear(),
                                movie.getDirector(),
                                rating,
                                genreDTOs,
                                starDTOs);
        }
}
