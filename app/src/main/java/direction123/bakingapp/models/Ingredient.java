package direction123.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fangxiangwang on 8/9/17.
 */

public class Ingredient implements Parcelable {
    private double mQuantity;
    private String mMeasure;
    private String mIngredient;

    public Ingredient(double quantity, String measure, String ingredient) {
        this.mQuantity = quantity;
        this.mMeasure = measure;
        this.mIngredient = ingredient;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.mQuantity);
        dest.writeString(this.mMeasure);
        dest.writeString(this.mIngredient);
    }

    protected Ingredient(Parcel in) {
        this.mQuantity = in.readDouble();
        this.mMeasure = in.readString();
        this.mIngredient = in.readString();
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
}
