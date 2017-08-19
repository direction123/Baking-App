package direction123.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fangxiangwang on 8/9/17.
 */

public class Recipe implements Parcelable {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> mIngredients = null;
    @SerializedName("steps")
    @Expose
    private List<Step> mSteps = null;
    @SerializedName("servings")
    @Expose
    private int mServings;
    @SerializedName("image")
    @Expose
    private String mImage;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getIngredients() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mIngredients.size(); i++) {
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

    public List<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(List<Step> steps) {
        this.mSteps = steps;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int servings) {
        this.mServings = servings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
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
