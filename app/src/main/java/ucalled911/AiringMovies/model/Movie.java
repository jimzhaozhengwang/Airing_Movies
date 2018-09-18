package ucalled911.AiringMovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("vote_count")
    int voteCount;

    @SerializedName("id")
    int id;

    @SerializedName("vote_average")
    double voteAverage;

    @SerializedName("title")
    String title;

    @SerializedName("popularity")
    double popularity;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("original_language")
    String originalLanguage;

    @SerializedName("original_title")
    String originalTitle;

    @SerializedName("genre_id")
    List<Integer> genreIds;

    @SerializedName("backdrop_path")
    String backdropPath;

    @SerializedName("adult")
    boolean adult;

    @SerializedName("overview")
    String overview;

    @SerializedName("release_date")
    String releaseDate;

    public Movie() {

    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getId() {
        return id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
