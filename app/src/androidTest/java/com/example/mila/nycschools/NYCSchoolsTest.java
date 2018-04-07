package com.example.mila.nycschools;


import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import com.example.mila.nycschools.model.NYCSchools;
import com.example.mila.nycschools.model.SATResponse;
import com.example.mila.nycschools.model.SchoolResponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NYCSchoolsTest {

  @Test
  public void test_NYCSchools_is_parcelable(){

        SATResponse sat=new SATResponse();
        sat.setId("123");
        SchoolResponse school = new SchoolResponse();
        school.setId("456");
        NYCSchools schools = new NYCSchools(sat,school);

        Parcel parcel = Parcel.obtain();
        schools.writeToParcel(parcel, schools.describeContents());
        parcel.setDataPosition(0);

        NYCSchools createdFromParcel = NYCSchools.CREATOR.createFromParcel(parcel);
        assertEquals(createdFromParcel.getmSAT().getId(), "123");
        assertEquals(createdFromParcel.getmSchools().getId(), "456");
    }
}
