package com.codepath.android.codepathandroidflicks.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("vote_count")
    int vote_count;

    @SerializedName("id")
    int id;

    @SerializedName("vote_average")
    double vote_average;

    @SerializedName("title")
    String title;

    @SerializedName("popularity")
    double popularity;

    @SerializedName("poster_path")
    String poster_path;

    @SerializedName("original_language")
    String original_language;

    @SerializedName("original_title")
    String original_title;

    @SerializedName("genre_id")
    List<Integer> genre_ids;

    @SerializedName("backdrop_path")
    String backdrop_path;

    @SerializedName("adult")
    boolean adult;

    @SerializedName("overview")
    String overview;

    @SerializedName("release_date")
    String release_date;

    public Movie(){

    }

    public int getVote_count() {
        return vote_count;
    }

    public int getId() {
        return id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }
}
