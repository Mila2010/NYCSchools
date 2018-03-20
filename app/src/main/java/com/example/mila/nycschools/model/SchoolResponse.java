package com.example.mila.nycschools.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mila on 3/20/18.
 */

public class SchoolResponse implements Parcelable {
    @SerializedName("dbn")
    private String mId;
    private String mBorough;
    @SerializedName("interest1")
    private String mMainStudy;
    @SerializedName("interest2")
    private String mSecondStudy;
    @SerializedName("overview_paragraph")
    private String mOverview;
    @SerializedName("phone_number")
    private String mPhoneNumber;
    @SerializedName("school_name")
    private String mSchoolName;
    @SerializedName("state_code")
    private String mState;
    private String mWebsite;
    private String mZip;

    private SchoolResponse(Parcel in) {
        mId = in.readString();
        mBorough = in.readString();
        mMainStudy = in.readString();
        mSecondStudy = in.readString();
        mOverview = in.readString();
        mPhoneNumber = in.readString();
        mSchoolName = in.readString();
        mState = in.readString();
        mWebsite = in.readString();
        mZip = in.readString();
    }

    public static final Creator<SchoolResponse> CREATOR = new Creator<SchoolResponse>() {
        @Override
        public SchoolResponse createFromParcel(Parcel in) {
            return new SchoolResponse(in);
        }

        @Override
        public SchoolResponse[] newArray(int size) {
            return new SchoolResponse[size];
        }
    };

    public String getId() {
        return mId;
    }

    public String getBorough() {
        return mBorough;
    }

    public String getMainStudy() {
        return mMainStudy;
    }

    public String getSecondStudy() {
        return mSecondStudy;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getSchoolName() {
        return mSchoolName;
    }

    public String getWebsite() {
        return mWebsite;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mBorough);
        dest.writeString(mMainStudy);
        dest.writeString(mSecondStudy);
        dest.writeString(mOverview);
        dest.writeString(mPhoneNumber);
        dest.writeString(mSchoolName);
        dest.writeString(mState);
        dest.writeString(mWebsite);
        dest.writeString(mZip);
    }
}
