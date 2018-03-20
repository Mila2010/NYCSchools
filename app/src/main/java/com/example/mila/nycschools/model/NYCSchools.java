package com.example.mila.nycschools.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mila on 3/20/18.
 */

public class NYCSchools implements Parcelable {

    private SATResponse mSAT;
    private SchoolResponse mSchools;

    public NYCSchools(SATResponse sat,SchoolResponse school){

        this.mSAT=sat;
        this.mSchools=school;
    }

    protected NYCSchools(Parcel in) {
        mSAT = in.readParcelable(SATResponse.class.getClassLoader());
        mSchools = in.readParcelable(SchoolResponse.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mSAT, flags);
        dest.writeParcelable(mSchools, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NYCSchools> CREATOR = new Creator<NYCSchools>() {
        @Override
        public NYCSchools createFromParcel(Parcel in) {
            return new NYCSchools(in);
        }

        @Override
        public NYCSchools[] newArray(int size) {
            return new NYCSchools[size];
        }
    };

    public SATResponse getmSAT() {
        return mSAT;
    }

    public SchoolResponse getmSchools() {
        return mSchools;
    }
}

