package com.example.mila.nycschools.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Toast;

import com.example.mila.nycschools.ProgressDialog;
import com.example.mila.nycschools.R;
import com.example.mila.nycschools.ViewPresenterContract;
import com.example.mila.nycschools.model.NYCSchools;
import com.example.mila.nycschools.presenter.Presenter;

import java.util.List;

import static com.example.mila.nycschools.model.Constants.SCHOOL_DIALOG;

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
        Presenter presenter = new Presenter(this);
        mDialog = new ProgressDialog(this, R.drawable.spinner);
        initRecyclerView();
        mPresenter.setClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    private void setSchoolList(List<NYCSchools> schools) {
        mAdapter.setSchools(schools);
    }
}
