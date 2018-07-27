package com.tamlove.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Steps implements Parcelable {

    @SerializedName("id")
    private int mId;
    @SerializedName("shortDescription")
    private String mShortDescription;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("videoURL")
    private String mVideoURL;
    @SerializedName("thumbnailURL")
    private String mThumbnailURL;

    public Steps(int id, String shortDescription, String description, String videoURL, String thumbnailURL){
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoURL = videoURL;
        mThumbnailURL = thumbnailURL;
    }

    public int getId(){
        return mId;
    }

    public String getShortDescription(){
        if(mShortDescription == null){
            return "";
        } else {
            return mShortDescription;
        }
    }

    public String getDescription(){
        if(mDescription == null){
            return "";
        } else {
            return mDescription;
        }
    }

    public String getVideoURL(){
        if(mVideoURL == null){
            return "";
        } else {
            return mVideoURL;
        }
    }

    public String getThumbnailURL(){
        if(mThumbnailURL == null){
            return "";
        } else {
            return mThumbnailURL;
        }
    }

    public void setId(int id){
        mId = id;
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
                "mId='" + mId + '\'' +
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
        parcel.writeInt(mId);
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoURL);
        parcel.writeString(mThumbnailURL);
    }

    protected Steps(Parcel in) {
        mId = in.readInt();
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
