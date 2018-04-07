package com.example.mila.nycschools;


import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import com.example.mila.nycschools.model.NYCSchools;
import com.example.mila.nycschools.model.SATResponse;
import com.example.mila.nycschools.model.SchoolResponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SatResponseTest {

    @Test
    public void test_SATResponse_is_parcelable(){

        SATResponse sat=new SATResponse();
        sat.setId("123");
        Parcel parcel = Parcel.obtain();
        sat.writeToParcel(parcel, sat.describeContents());
        parcel.setDataPosition(0);
        SATResponse createdFromParcel = SATResponse.CREATOR.createFromParcel(parcel);
        assertEquals(createdFromParcel.getId(), "123");

    }


}
