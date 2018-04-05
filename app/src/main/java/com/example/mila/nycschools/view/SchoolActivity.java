package com.example.mila.nycschools.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Toast;

import com.example.mila.nycschools.ProgressDialog;
import com.example.mila.nycschools.R;
import com.example.mila.nycschools.SavedInstanceFragment;
import com.example.mila.nycschools.ViewPresenterContract;
import com.example.mila.nycschools.model.NYCSchools;
import com.example.mila.nycschools.model.RepositoryImpl;
import com.example.mila.nycschools.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

import static com.example.mila.nycschools.model.AppConstants.SAVED_INSTANCE;
import static com.example.mila.nycschools.model.AppConstants.SCHOOL_DIALOG;
import static com.example.mila.nycschools.model.AppConstants.SCHOOL_LIST;

/**
 * Created by mila on 3/20/18.
 */

public class SchoolActivity extends AppCompatActivity implements ViewPresenterContract.View<ViewPresenterContract.Presenter>{

    private SchoolAdapter mAdapter;
    private ProgressDialog mDialog;
    private List<NYCSchools> mSchools;
    private Boolean mIsFetchRequired = true;
    public ViewPresenterContract.Presenter mPresenter;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools);
        RepositoryImpl repository = RepositoryImpl.getINSTANCE();
        Presenter presenter = new Presenter(this,repository);
        mDialog = new ProgressDialog(this, R.drawable.spinner);
        initRecyclerView();

        //if onSaveInstanceState has been called perhaps because of screen rotation, then object of SavedInstanceFragment was created
        // and list of schools saved, so it is still in memory and can be found by tag.
        SavedInstanceFragment schoolsData = (SavedInstanceFragment) getSupportFragmentManager().findFragmentByTag(SAVED_INSTANCE);
        if (schoolsData != null) {
            //retrieving school data from fragment
            mSchools = schoolsData.popData().getParcelableArrayList(SCHOOL_LIST);
            setSchoolList(mSchools);
            mIsFetchRequired = false;
        } else {
            mIsFetchRequired = true;
        }
        mPresenter.setClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check if it is required to fetch list of schools from network, or if it's being saved
        if (mIsFetchRequired) {
            mDialog.show();
            mPresenter.connect();
            mIsFetchRequired = false;
        }

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
        mDialog.dismiss();
        setSchoolList(schools);

    }

    @Override
    public void setPresenter(ViewPresenterContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void showError() {
        mDialog.dismiss();
        //If have more time would show FragmentDialog overlay in the case of error with "OK" button to dismiss pop up
        Toast errorMessage = Toast.makeText(this, getResources().getString(R.string.error_message), Toast.LENGTH_LONG);
        errorMessage.setGravity(Gravity.CENTER, 0, 0);
        errorMessage.show();

    }

    @Override
    public void showSchool(NYCSchools nycSchool) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SCHOOL_DIALOG);
        if (fragment != null) {
            transaction.remove(fragment);
        }
        transaction.addToBackStack(null);
        SchoolDialog dialog = SchoolDialog.newInstance(nycSchool);
        dialog.show(transaction, SCHOOL_DIALOG);

    }

    @Override
    public SchoolAdapter getmAdapter() {
        return mAdapter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.getmDisposable().dispose();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //saving list of school to handle rotation case, saving data straight in Bundle causing TransactionTooLargeException
        //Google is suggesting to do it with Fragment that retains INSTANCE.

        if (mSchools != null) {
            Bundle schoolsData = new Bundle();
            schoolsData.putParcelableArrayList(SCHOOL_LIST, (ArrayList<? extends Parcelable>) mSchools);
            SavedInstanceFragment.getInstance(getSupportFragmentManager()).pushData((Bundle) schoolsData);
        }
        super.onSaveInstanceState(outState);
    }

    private void setSchoolList(List<NYCSchools> schools) {
        mAdapter.setSchools(schools);
    }
}
