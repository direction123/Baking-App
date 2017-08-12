package direction123.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fangxiangwang on 8/9/17.
 */

public class Recipe implements Parcelable {
    int mId;
    String mName;
    List<Ingredient> mIngredients;
    List<Step> mSteps;
    int mServings;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings) {
        this.mId = id;
        this.mName = name;
        this.mIngredients = ingredients;
        this.mSteps = steps;
        this.mServings = servings;
    }

    public String getNmae() {
        return mName;
    }

    public int getServings() {
        return mServings;
    }

    public String getIngredients() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < mIngredients.size(); i++) {
            sb.append("\u2022 ");
            sb.append(mIngredients.get(i).getIngredient());
            sb.append(" (");
            sb.append(mIngredients.get(i).getQuantity());
            sb.append(" ");
            sb.append(mIngredients.get(i).getMeasure());
            sb.append(") ");
            sb.append("\n");
        }
        return sb.toString();
    }

    public List<Step> getStepList() {
        return mSteps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeTypedList(this.mIngredients);
        dest.writeTypedList(this.mSteps);
        dest.writeInt(this.mServings);
    }

    protected Recipe(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mIngredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.mSteps = in.createTypedArrayList(Step.CREATOR);
        this.mServings = in.readInt();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
