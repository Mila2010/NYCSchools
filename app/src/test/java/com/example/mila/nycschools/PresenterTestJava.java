package com.example.mila.nycschools;



import com.example.mila.nycschools.model.NYCSchools;
import com.example.mila.nycschools.model.RepositoryImpl;
import com.example.mila.nycschools.presenter.Presenter;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


/**
 * Created by mila on 4/4/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class PresenterTestJava {

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    private ViewPresenterContract.View mView;

    @Mock
    private RepositoryImpl mRepo;

    private Presenter mPresenter;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mPresenter= new Presenter(mView,mRepo);


    }

    @Test
    public void testViewSet() {
        verify(mView).setPresenter(mPresenter);

    }

    @Test
    public void testConnect_Successful() {
        List<NYCSchools> list = new ArrayList<>();
        Mockito.doNothing().when(mRepo).initNYCSchools();
        Mockito.when(mRepo.getSchools()).thenReturn( Observable.just(list));
        mPresenter.connect();
        verify(mView).showList(any());
    }

    @Test
    public void testConnect_Unsuccessful() {
        Mockito.doNothing().when(mRepo).initNYCSchools();
        Mockito.when(mRepo.getSchools()).thenReturn(Observable.error(new Exception("dummy exception")));
        mPresenter.connect();
        verify(mView).showError();
    }


}
