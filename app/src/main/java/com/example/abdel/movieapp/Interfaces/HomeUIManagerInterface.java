package com.example.abdel.movieapp.Interfaces;

import com.example.abdel.movieapp.Models.Movie;

import java.util.List;

/**
 * Created by abdel on 10/22/2017.
 */

public interface HomeUIManagerInterface {
    void showError();
    void showData(List<Movie> moviesList);
    void HandelProgressBar(int visibility);
}
