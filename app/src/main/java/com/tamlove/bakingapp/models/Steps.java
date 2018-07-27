package com.tamlove.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Steps implements Parcelable {

    @SerializedName("shortDescription")
    private String mShortDescription;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("videoURL")
    private String mVideoURL;
    @SerializedName("thumbnailURL")
    private String mThumbnailURL;

    public Steps(String shortDescription, String description, String videoURL, String thumbnailURL){
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoURL = videoURL;
        mThumbnailURL = thumbnailURL;
    }

    public String getShortDescription(){
        return mShortDescription;
    }

    public String getDescription(){
        return mDescription;
    }

    public String getVideoURL(){
        return mVideoURL;
    }

    public String getThumbnailURL(){
        return mThumbnailURL;
    }

    public void setShortDescription(String shortDescription){
        mShortDescription = shortDescription;
    }

    public void setDescription(String description){
        mDescription = description;
    }

    public void setVideoURL(String videoURL){
        mVideoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL){
        mThumbnailURL = thumbnailURL;
    }

    @Override
    public String toString() {
        return "Steps{" +
                "mShortDescription='" + mShortDescription + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mVideoURL='" + mVideoURL + '\'' +
                ", mThumbnailURL='" + mThumbnailURL + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoURL);
        parcel.writeString(mThumbnailURL);
    }

    protected Steps(Parcel in) {
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoURL = in.readString();
        mThumbnailURL = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}
