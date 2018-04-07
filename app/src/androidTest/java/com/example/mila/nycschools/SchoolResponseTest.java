package com.example.mila.nycschools;


import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import com.example.mila.nycschools.model.SATResponse;
import com.example.mila.nycschools.model.SchoolResponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SchoolResponseTest {

    @Test
    public void test_SchoolResponse_is_parcelable(){

        SchoolResponse school=new SchoolResponse();
        school.setId("123");
        Parcel parcel = Parcel.obtain();
        school.writeToParcel(parcel, school.describeContents());
        parcel.setDataPosition(0);
        SchoolResponse createdFromParcel = SchoolResponse.CREATOR.createFromParcel(parcel);
        assertEquals(createdFromParcel.getId(), "123");

    }
}
