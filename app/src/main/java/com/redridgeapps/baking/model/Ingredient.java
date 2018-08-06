package com.redridgeapps.baking.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

public class Ingredient implements Parcelable {

    @Json(name = "quantity")
    private Float quantity;

    @Json(name = "measure")
    private String measure;

    @Json(name = "ingredient")
    private String ingredient;

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Ingredient() {
    }

    private Ingredient(Parcel in) {
        this.quantity = (Float) in.readValue(Float.class.getClassLoader());
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }
}
