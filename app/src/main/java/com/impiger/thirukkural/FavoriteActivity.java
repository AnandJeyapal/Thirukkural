package com.impiger.thirukkural;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.impiger.thirukkural.fragment.FavoriteFragment;
import com.impiger.thirukkural.fragment.FavoriteKuralFragment;
import com.impiger.thirukkural.model.Constants;

public class FavoriteActivity extends AppCompatActivity {
    private int selectedFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (selectedFragment != item.getItemId()) {
                switch (item.getItemId()) {
                    case R.id.navigation_adhigarams:
                        navigateFragment(Constants.ADHIGARAMS_FRAGMENT);
                        selectedFragment = item.getItemId();
                        return true;
                    case R.id.navigation_kurals:
                        navigateFragment(Constants.KURALS_FRAGMENT);
                        selectedFragment = item.getItemId();
                        return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigateFragment(Constants.ADHIGARAMS_FRAGMENT);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void navigateFragment(int fragmentId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (fragmentId) {
            case Constants.ADHIGARAMS_FRAGMENT:
                fragment = new FavoriteFragment();
                break;
            case Constants.KURALS_FRAGMENT:
                fragment = new FavoriteKuralFragment();
                break;
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
