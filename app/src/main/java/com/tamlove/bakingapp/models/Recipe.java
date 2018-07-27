package com.tamlove.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements Parcelable {

    @SerializedName("name")
    private String mName;
    @SerializedName("ingredients")
    private List<Ingredients> mIngredients;
    @SerializedName("steps")
    private List<Steps> mSteps;
    @SerializedName("servings")
    private String mServings;
    @SerializedName("image")
    private String mImage;

    public Recipe(String name, List<Ingredients> ingredients, List<Steps> steps, String servings, String image){
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImage = image;
    }

    public String getName(){
        if(mImage == null){
            return "";
        } else {
            return mName;
        }
    }

    public List<Ingredients> getIngredients(){
        return mIngredients;
    }

    public List<Steps> getSteps(){
        return mSteps;
    }

    public String getServings(){
        if(mServings == null){
            return "";
        } else {
            return mServings;
        }
    }

    public String getImage(){
        if(mImage == null){
            return "";
        } else {
            return mImage;
        }
    }

    public void setName(String name){
        mName = name;
    }

    public void setIngredients(List<Ingredients> ingredients){
        mIngredients = ingredients;
    }

    public void setSteps(List<Steps> steps){
        mSteps = steps;
    }

    public void setServings(String servings){
        mServings = servings;
    }

    public void setImage(String image){
        mImage = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "mName='" + mName + '\'' +
                ", mIngredients=" + mIngredients +
                ", mSteps=" + mSteps +
                ", mServings='" + mServings + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeTypedList(mIngredients);
        parcel.writeTypedList(mSteps);
        parcel.writeString(mServings);
        parcel.writeString(mImage);
    }

    protected Recipe(Parcel in) {
        mName = in.readString();
        mIngredients = in.createTypedArrayList(Ingredients.CREATOR);
        mSteps = in.createTypedArrayList(Steps.CREATOR);
        mServings = in.readString();
        mImage = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
