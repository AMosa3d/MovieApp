package com.example.abdel.movieapp.Interfaces;

import com.example.abdel.movieapp.Models.Review;
import com.example.abdel.movieapp.Models.Trailer;

import java.util.List;

/**
 * Created by abdel on 10/22/2017.
 */

public interface DetailManagerInterface {
    void showError();
    void setTrailersList(List<Trailer> trailersList);
    void setReviewsList(List<Review> reviewsList);
    void HandelProgressBar(int visibility);
    void onTrailerClick(Trailer trailer);
}
