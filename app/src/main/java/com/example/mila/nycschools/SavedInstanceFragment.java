package com.example.mila.nycschools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import static com.example.mila.nycschools.model.Constants.SAVED_INSTANCE;

/**
 * Created by mila on 3/20/18.
 */

public class SavedInstanceFragment extends Fragment {

    private Bundle mInstanceBundle = null;

    public SavedInstanceFragment() {
        super();
        setRetainInstance( true );
    }

    public SavedInstanceFragment pushData( Bundle instanceState )
    {
        if ( this.mInstanceBundle == null ) {
            this.mInstanceBundle = instanceState;
        }
        else
        {
            this.mInstanceBundle.putAll( instanceState );
        }
        return this;
    }

    public Bundle popData()
    {
        Bundle out = this.mInstanceBundle;
        this.mInstanceBundle = null;
        return out;
    }

    public static  SavedInstanceFragment getInstance(FragmentManager fragmentManager )
    {
        SavedInstanceFragment out = (SavedInstanceFragment) fragmentManager.findFragmentByTag(SAVED_INSTANCE);

        if ( out == null )
        {
            out = new SavedInstanceFragment();
            fragmentManager.beginTransaction().add( out, SAVED_INSTANCE ).commit();
        }
        return out;
    }

}

