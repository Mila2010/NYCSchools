package com.example.mila.nycschools.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.mila.nycschools.R;
import com.example.mila.nycschools.ViewPresenterContract;
import com.example.mila.nycschools.model.NYCSchools;
import com.example.mila.nycschools.presenter.Presenter;

import java.util.List;

/**
 * Created by mila on 3/20/18.
 */

public class SchoolActivity extends AppCompatActivity implements ViewPresenterContract.View<ViewPresenterContract.Presenter>{

    private SchoolAdapter mAdapter;

    private List<NYCSchools> mSchools;
    private Boolean mIsFetchRequired = true;
    public ViewPresenterContract.Presenter mPresenter;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools);
        Presenter presenter = new Presenter(this);
        initRecyclerView();
        mPresenter.setClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.connect();
    }

    public void initRecyclerView() {
        mAdapter = new SchoolAdapter();
        RecyclerView schools = findViewById(R.id.school_list);
        schools.setLayoutManager(new LinearLayoutManager(this));
        schools.setAdapter(mAdapter);
    }

    @Override
    public void showList(List<NYCSchools> schools) {
        mSchools = schools;
        setSchoolList(schools);

    }

    @Override
    public void setPresenter(ViewPresenterContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showError() {

    }

    @Override
    public void showSchool(NYCSchools nycSchool) {

    }

    @Override
    public SchoolAdapter getmAdapter() {
        return mAdapter;
    }

    private void setSchoolList(List<NYCSchools> schools) {
        mAdapter.setSchools(schools);
    }
}
