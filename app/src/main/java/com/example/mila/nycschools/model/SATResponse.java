package com.example.mila.nycschools.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mila on 3/20/18.
 */

public class SATResponse implements Parcelable {

    @SerializedName("dbn")
    private String mId;
    @SerializedName("sat_critical_reading_avg_score")
    private String mReadingScore;
    @SerializedName("sat_math_avg_score")
    private String mMathScore;
    @SerializedName("sat_writing_avg_score")
    private String mWritingScore;


    private SATResponse(Parcel in) {
        mId = in.readString();
        mReadingScore = in.readString();
        mMathScore = in.readString();
        mWritingScore = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mReadingScore);
        dest.writeString(mMathScore);
        dest.writeString(mWritingScore);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SATResponse> CREATOR = new Creator<SATResponse>() {
        @Override
        public SATResponse createFromParcel(Parcel in) {
            return new SATResponse(in);
        }

        @Override
        public SATResponse[] newArray(int size) {
            return new SATResponse[size];
        }
    };

    public String getId() {
        return mId;
    }

    public String getReadingScore() {
        return mReadingScore;
    }

    public String getMathScore() {
        return mMathScore;
    }

    public String getWritingScore() {
        return mWritingScore;
    }

}
