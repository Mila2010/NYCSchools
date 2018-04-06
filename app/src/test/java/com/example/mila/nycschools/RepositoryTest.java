package com.example.mila.nycschools;

import com.example.mila.nycschools.model.NYCSchools;
import com.example.mila.nycschools.model.RepositoryImpl;
import com.example.mila.nycschools.model.SATResponse;
import com.example.mila.nycschools.model.SchoolResponse;
import com.example.mila.nycschools.network.Api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;


/**
 * Created by mila on 4/4/18.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class RepositoryTest {

    private RepositoryImpl mRepo;

    @Mock
    private Api mService;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mRepo= RepositoryImpl.getINSTANCE();
    }

    @Test
    public void test_initSchools() {
       Mockito.when(mService.getSchools(any())).thenReturn( Observable.just(initScools()));
       Mockito.when(mService.getSAT(any())).thenReturn( Observable.just(initSat()));
        mRepo.initNYCSchools();
        List<NYCSchools> schools = mRepo.getSchools().toList().blockingGet().get(0);
        assertEquals(true,compareIds(schools));
    }

    private List<SATResponse> initSat() {
        List<SATResponse> sat = new ArrayList<>();
        SATResponse school1= new SATResponse();
        SATResponse school2= new SATResponse();
        SATResponse school3= new SATResponse();
        school1.setId("3");
        school2.setId("2");
        school3.setId("1");
        sat.add(school1);
        sat.add(school2);
        sat.add(school3);

        return sat;
    }

    private List<SchoolResponse> initScools() {

        List<SchoolResponse> schools = new ArrayList<>();
        SchoolResponse school1= new SchoolResponse();
        SchoolResponse school2= new SchoolResponse();
        SchoolResponse school3= new SchoolResponse();
        school1.setId("1");
        school2.setId("2");
        school3.setId("3");
        schools.add(school1);
        schools.add(school2);
        schools.add(school3);
         return schools;
    }

    private boolean compareIds(List<NYCSchools> nycSchools){

        for (int i=0;i<nycSchools.size();i++){
            if(!nycSchools.get(i).getmSchools()
                    .getId().equalsIgnoreCase(nycSchools.get(i).getmSAT().getId())){
                return false;
            }
        }

       return true;
    }

}
