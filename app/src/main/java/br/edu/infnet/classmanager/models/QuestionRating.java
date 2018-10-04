package br.edu.infnet.classmanager.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class QuestionRating implements Serializable {

    private String ratingUserId;
    private float rating;

    public QuestionRating() {
    }

    public QuestionRating(String ratingUserId, float rating) {
        this.ratingUserId = ratingUserId;
        this.rating = rating;
    }

    public String getRatingUserId() {
        return ratingUserId;
    }

    public float getRating() {
        return rating;
    }

    public void setRatingUserId(String ratingUserId) {
        this.ratingUserId = ratingUserId;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
