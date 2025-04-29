package com.CinephileLog.controller;

import com.CinephileLog.domain.Movie;
import com.CinephileLog.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/search")
    public String searchMovies(@RequestParam("title") String title, Model model) {
        List<Movie> movies = movieService.findMoviesByTitle(title);
        model.addAttribute("movies", movies);
        model.addAttribute("keyword", title);
        return "searchResult";
    }
}