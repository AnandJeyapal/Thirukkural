package com.impiger.thirukkural;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.impiger.thirukkural.adapter.AdhigaramAdapter;
import com.impiger.thirukkural.adapter.AdhigaramPageAdapter;
import com.impiger.thirukkural.database.DBHelper;
import com.impiger.thirukkural.fragment.AramFragment;
import com.impiger.thirukkural.model.Adhigaram;
import com.impiger.thirukkural.model.Model;
import com.impiger.thirukkural.model.Thirukkural;
import com.impiger.thirukkural.view.SlidingTabLayout;

import java.util.ArrayList;

/**
 * Created by anand on 07/12/15.
 */
public class AdhigaramListActivity extends AppCompatActivity {
    private DBHelper myDbHelper;
    private Toolbar toolbar;
    private AdhigaramPageAdapter mAdapter;
    private boolean noSearchQuery = true;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private final static int ARAM_FRAGMENT = 0;
    private final static int PORUL_FRAGMENT = 1;
    private final static int INBAM_FRAGMENT = 2;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhigaram_list);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        loadDB();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("அறம்"));
        tabLayout.addTab(tabLayout.newTab().setText("பொருள்"));
        tabLayout.addTab(tabLayout.newTab().setText("இன்பம்"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) findViewById(R.id.adhigaram_view_pager);
        mAdapter = new AdhigaramPageAdapter(getSupportFragmentManager()
                , tabLayout.getTabCount());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adhigaram_list, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("அதிகார எண்");
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    noSearchQuery = false;
                    navigatePage(newText);
                } else if (!noSearchQuery) {
                    noSearchQuery = true;
                    ((AramFragment) mAdapter.getItem(mViewPager.getCurrentItem())).resetScreen();
                }
                return false;
            }
        });
        return true;
    }

    private void navigatePage(String adhigaramNumberStr) {
        int adhigaramNumber = Integer.parseInt(adhigaramNumberStr);
        int currentPage = mViewPager.getCurrentItem();
        if(adhigaramNumber <= 133) {
            if(adhigaramNumber <= 38 && currentPage != ARAM_FRAGMENT) {
                mViewPager.setCurrentItem(ARAM_FRAGMENT);
                ((AramFragment) mAdapter.getItem(mViewPager.getCurrentItem())).resetScreen();
                ((AramFragment) mAdapter.getItem(mViewPager.getCurrentItem())).doSearch(adhigaramNumberStr);
            } else if (adhigaramNumber > 38 && adhigaramNumber <= 109 && currentPage != PORUL_FRAGMENT) {
                mViewPager.setCurrentItem(PORUL_FRAGMENT);
                ((AramFragment) mAdapter.getItem(mViewPager.getCurrentItem())).resetScreen();
                ((AramFragment) mAdapter.getItem(mViewPager.getCurrentItem())).doSearch(adhigaramNumberStr);
            } else if (adhigaramNumber > 109 && adhigaramNumber <=133 && currentPage != INBAM_FRAGMENT) {
                mViewPager.setCurrentItem(INBAM_FRAGMENT);
                ((AramFragment) mAdapter.getItem(mViewPager.getCurrentItem())).resetScreen();
                ((AramFragment) mAdapter.getItem(mViewPager.getCurrentItem())).doSearch(adhigaramNumberStr);
            } else {
                ((AramFragment) mAdapter.getItem(mViewPager.getCurrentItem())).doSearch(adhigaramNumberStr);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void loadDB() {
        myDbHelper = new DBHelper(this);
    }

}
