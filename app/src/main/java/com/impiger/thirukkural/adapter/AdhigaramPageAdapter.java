package com.impiger.thirukkural.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.impiger.thirukkural.fragment.AramFragment;
import com.impiger.thirukkural.model.Adhigaram;
import com.impiger.thirukkural.model.Constants;

import java.util.ArrayList;

/**
 * Created by anand on 17/12/15.
 */

public class AdhigaramPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    AramFragment aramFragment;
    AramFragment porulFragment;
    AramFragment inbamFragment;

    public AdhigaramPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        constructFragments();
    }

    private void constructFragments() {
        aramFragment = new AramFragment();
        Bundle args = new Bundle();
        args.putString(Constants.PART_NAME, Constants.FIRST_PART);
        aramFragment.setArguments(args);

        porulFragment = new AramFragment();
        args = new Bundle();
        args.putString(Constants.PART_NAME, Constants.SECOND_PART);
        porulFragment.setArguments(args);

        inbamFragment = new AramFragment();
        args = new Bundle();
        args.putString(Constants.PART_NAME, Constants.THIRD_PART);
        inbamFragment.setArguments(args);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return aramFragment;
            case 1:
                return porulFragment;
            case 2:
                return inbamFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
