package com.example.mila.nycschools;

import com.example.mila.nycschools.model.NYCSchools;
import com.example.mila.nycschools.view.SchoolAdapter;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by mila on 3/20/18.
 */

public interface ViewPresenterContract {

    interface View<Presenter> {


        void showList(List<NYCSchools> schools);

        void setPresenter(Presenter presenter);

        void showError();

        void showSchool(NYCSchools nycSchool);

        SchoolAdapter getmAdapter();

    }

    interface Presenter {

        void connect();

        CompositeDisposable getmDisposable();

        void setClickListener();
    }

}
