package com.tamlove.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredients implements Parcelable {

    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("measure")
    private String mMeasure;
    @SerializedName("ingredient")
    private String mIngredient;

    public Ingredients(String quantity, String measure, String ingredient){
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    public String getQuantity(){
        if(mQuantity == null){
            return "";
        } else {
            return mQuantity;
        }
    }

    public String getMeasure(){
        if(mMeasure == null){
            return "";
        } else {
            return mMeasure;
        }
    }

    public String getIngredient(){
        if(mIngredient == null){
            return "";
        } else {
            return mIngredient;
        }
    }

    public void setQuantity(String quantity){
        mQuantity = quantity;
    }

    public void setMeasure(String measure){
        mMeasure = measure;
    }

    public void setIngredient(String ingredient){
        mIngredient = ingredient;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "mQuantity='" + mQuantity + '\'' +
                ", mMeasure='" + mMeasure + '\'' +
                ", mIngredient='" + mIngredient + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mQuantity);
        parcel.writeString(mMeasure);
        parcel.writeString(mIngredient);
    }

    protected Ingredients(Parcel in) {
        mQuantity = in.readString();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };
}
