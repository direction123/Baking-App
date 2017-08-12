package direction123.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fangxiangwang on 8/9/17.
 */

public class Step implements Parcelable {
    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;

    public Step(int id, String shortDescription, String description, String videoURL) {
        this.mId = id;
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mVideoURL = videoURL;
    }

    public int getId() {
        return mId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
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
