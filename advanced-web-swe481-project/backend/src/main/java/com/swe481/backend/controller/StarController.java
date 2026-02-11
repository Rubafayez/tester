package com.swe481.backend.controller;

import com.swe481.backend.dto.MovieDTO;
import com.swe481.backend.dto.StarDetailsDTO;
import com.swe481.backend.entity.Star;
import com.swe481.backend.repository.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stars")
@CrossOrigin(origins = "http://localhost:4200")
public class StarController {

    @Autowired
    private StarRepository starRepository;

    @GetMapping("/{id}")
    public StarDetailsDTO getStarById(@PathVariable String id) {
        return starRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    private StarDetailsDTO convertToDTO(Star star) {
        List<MovieDTO> movieDTOs = star.getMovies().stream()
                .map(m -> new MovieDTO(
                        m.getId(),
                        m.getTitle(),
                        m.getYear(),
                        m.getDirector(),
                        null, // Rating not needed here or fetch lazily
                        null, // Genres not needed here
                        null // Stars not needed here to avoid recursion
                ))
                .collect(Collectors.toList());

        return new StarDetailsDTO(
                star.getId(),
                star.getName(),
                star.getBirthYear(),
                movieDTOs);
    }
}
