package direction123.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fangxiangwang on 8/9/17.
 */

public class Step implements Parcelable {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("shortDescription")
    @Expose
    private String mShortDescription;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("videoURL")
    @Expose
    private String mVideoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String mThumbnailURL;

    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.mId = id;
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mVideoURL = videoURL;
        this.mThumbnailURL = thumbnailURL;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.mShortDescription = shortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public void setVideoURL(String videoURL) {
        this.mVideoURL = videoURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.mThumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mShortDescription);
        dest.writeString(this.mDescription);
        dest.writeString(this.mVideoURL);
    }

    protected Step(Parcel in) {
        this.mId = in.readInt();
        this.mShortDescription = in.readString();
        this.mDescription = in.readString();
        this.mVideoURL = in.readString();
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
}
