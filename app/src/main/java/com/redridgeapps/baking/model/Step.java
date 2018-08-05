package com.redridgeapps.baking.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.util.DiffUtil;

import com.squareup.moshi.Json;

public class Step implements Parcelable {

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

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public Step() {
    }

    private Step(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    public static final DiffUtil.ItemCallback<Step> DIFF_CALLBACK = new DiffUtil.ItemCallback<Step>() {

        @Override
        public boolean areItemsTheSame(Step oldItem, Step newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Step oldItem, Step newItem) {
            return true;
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Step)) return false;

        Step otherStep = (Step) obj;

        return this.getId() == otherStep.getId();
    }
}
