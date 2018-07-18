package com.tamlove.bakingapp.models;

import com.google.gson.annotations.SerializedName;

public class Ingredients {

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
        return mQuantity;
    }

    public String getMeasure(){
        return mMeasure;
    }

    public String getIngredient(){
        return mIngredient;
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
}
