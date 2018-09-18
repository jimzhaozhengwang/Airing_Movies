package ucalled911.AiringMovies.model;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    String id;

    @SerializedName("iso_639_1")
    String iso_639_1;

    @SerializedName("iso_3166_1")
    String iso_3166_1;

    @SerializedName("key")
    String key;

    @SerializedName("name")
    String name;

    @SerializedName("site")
    String site;

    @SerializedName("size")
    int size;

    @SerializedName("type")
    String type;

    public Video() {

    }

    public String getId() {
        return id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}

