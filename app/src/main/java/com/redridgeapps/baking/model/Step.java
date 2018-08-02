package com.redridgeapps.baking.model;

import com.squareup.moshi.Json;

public class Step {

    @Json(name = "id")
    private int id;

    @Json(name = "shortDescription")
    private String shortDescription;

    @Json(name = "description")
    private String description;

    @Json(name = "videoURL")
    private String videoURL;

    @Json(name = "thumbnailURL")
    private String thumbnailURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
