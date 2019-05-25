package com.impiger.thirukkural;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.impiger.thirukkural.adapter.DrawerAdapter;
import com.impiger.thirukkural.database.DBHelper;
import com.impiger.thirukkural.model.Constants;
import com.impiger.thirukkural.model.Model;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static String FACEBOOK_URL = "https://www.facebook.com/janand.boss";
    public static String FACEBOOK_PAGE_ID = "janand.boss";

    String TITLES[];
    int ICONS[] = {R.drawable.ic_home_white, R.drawable.ic_events_white, R.drawable.ic_star,
            R.drawable.ic_alarm, R.drawable.ic_help_white};
    private Toolbar toolbar;
    ListView listView;
    ListAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawer;
    private DBHelper myDbHelper;
    private Model model;
    ActionBarDrawerToggle mDrawerToggle;
    FloatingActionButton readKurals;
    private View moreSalamon;
    private View moreThirukkural;
    private View shareSalamon;
    private View shareThirukkural;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        TITLES = getResources().getStringArray(R.array.home_menus);
        moreSalamon = findViewById(R.id.more_about_salamon);
        moreThirukkural = findViewById(R.id.more_about_thirukkural);
        shareSalamon = findViewById(R.id.share_about_salamon);
        shareThirukkural = findViewById(R.id.share_about_thirukkural);
        moreSalamon.setOnClickListener(this);
        moreThirukkural.setOnClickListener(this);
        shareSalamon.setOnClickListener(this);
        shareThirukkural.setOnClickListener(this);
        model = Model.getInstance();
        loadDB();
        readKurals = (FloatingActionButton) findViewById(R.id.done_button);
        readKurals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        listView = (ListView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to
        // the xml View
        mAdapter = new DrawerAdapter(this, TITLES, ICONS);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 1:
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), AdhigaramListActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(), AlarmActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        showAboutDialog();
                        break;
                    default:
                        break;
                }
            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void showAboutDialog() {
        View messageView = getLayoutInflater().inflate(R.layout.about_dialog, null, false);
        View fbView = messageView.findViewById(R.id.facebook);
        fbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL();
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(messageView);
        builder.create();
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public String getFacebookPageURL() {
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
            return;
        }
        super.onBackPressed();
    }

    private void initModel() {
        model.setKurals(myDbHelper.getAllKurals());
        model.setAdhigarams(myDbHelper.getAllAdhigarams());
    }

    private void loadDB() {
        myDbHelper = new DBHelper(this);
        initModel();
    }

    @Override
    public void onClick(View v) {
        Intent moreContentIntent = new Intent(this, MoreContentActivity.class);
        ActivityOptionsCompat options = null;
        switch (v.getId()) {
            case R.id.more_about_salamon:
                moreContentIntent.putExtra(Constants.EXTRA_TITLE_KEY, Constants.EXTRA_MORE_THIRUKKURAL_POINTS);
                options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this, findViewById(R.id.sp_image), "thirukkural");
                startActivity(moreContentIntent, options.toBundle());
                break;
            case R.id.more_about_thirukkural:
                moreContentIntent.putExtra(Constants.EXTRA_TITLE_KEY, Constants.EXTRA_MORE_THIRUKKURAL);
                options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this, findViewById(R.id.thirukkural_image), "thirukkural");
                startActivity(moreContentIntent, options.toBundle());
                break;
            case R.id.share_about_salamon:
                Utils.shareContent(getApplicationContext(), Constants.EXTRA_MORE_THIRUKKURAL_POINTS);
                break;
            case R.id.share_about_thirukkural:
                Utils.shareContent(getApplicationContext(), Constants.EXTRA_MORE_THIRUKKURAL);
                break;

        }
    }
}
