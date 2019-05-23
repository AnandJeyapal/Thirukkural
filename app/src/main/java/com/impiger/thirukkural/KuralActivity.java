package com.impiger.thirukkural;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.impiger.thirukkural.adapter.DrawerAdapter;
import com.impiger.thirukkural.adapter.ThirukkuralPageAdapter;
import com.impiger.thirukkural.alarm.AlarmReceiver;
import com.impiger.thirukkural.database.DBHelper;
import com.impiger.thirukkural.dialogs.ShareKuralDialog;
import com.impiger.thirukkural.listeners.ShareKuralDialogListener;
import com.impiger.thirukkural.model.Constants;
import com.impiger.thirukkural.model.Model;
import com.impiger.thirukkural.model.Thirukkural;

import java.util.ArrayList;


public class KuralActivity extends AppCompatActivity implements ShareKuralDialogListener{

    String TITLES[] = {"முதற்பக்கம்", "அதிகாரங்கள் ", "விருப்பமானவை", "தினம் ஒரு குறள்","விவரம்"};
    int ICONS[] = {R.drawable.ic_home_white, R.drawable.ic_events_white, R.drawable.ic_star,
            R.drawable.ic_alarm, R.drawable.ic_help_white};
    private Toolbar toolbar;
    ListView listView;
    ListAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    private ViewPager contentsPager;
    private DBHelper myDbHelper;
    private ArrayList<Thirukkural> kurals;
    private int kuralStartId;
    private PendingIntent pendingIntent;
    private ShareKuralDialog dialog;
    private ThirukkuralPageAdapter adapter;
    private Model model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        contentsPager = (ViewPager) findViewById(R.id.contents_pager);
        listView = (ListView) findViewById(R.id.RecyclerView);
        kuralStartId = getIntent().getIntExtra(Constants.EXTRA_START_ID, 0);
        mAdapter = new DrawerAdapter(this, TITLES, ICONS);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 1:
                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), AdhigaramListActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), FavoriteGridActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(), AlarmActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        showAboutDialog();
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
        loadDB();
        kurals = myDbHelper.getAllKurals();
        adapter = new ThirukkuralPageAdapter(this, kurals);
        contentsPager.setAdapter(adapter);
        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        contentsPager.setCurrentItem(kuralStartId);
        dialog = new ShareKuralDialog("ShareKural", this);
    }

    public void startAt10() {
//        Intent myIntent = new Intent(KuralActivity.this , AlarmReceiver.class);
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        pendingIntent = PendingIntent.getService(KuralActivity.this, 0, myIntent, 0);
//        alarmManager.cancel(pendingIntent);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 18);
//        calendar.set(Calendar.MINUTE, 28);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
//        scheduleNotification(5000);
//        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 18);
//        calendar.set(Calendar.MINUTE, 18);
//        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void scheduleNotification(int delay) {

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_events);
        return builder.build();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kural_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("குறள் எண்");
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int index = Integer.parseInt(query);
                if (!query.isEmpty() && index < 1331) {
                    contentsPager.setCurrentItem(index - 1);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_kural_share) {
            showShareDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showShareDialog() {
        dialog.show(getSupportFragmentManager(), "ShareDialog");
    }

    private void shareKural(ArrayList<Integer> explanations) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        Thirukkural kural = kurals.get(contentsPager.getCurrentItem());
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, kural +"\n\n" + kural.getExplanations(explanations));
        startActivity(intent);
    }

    private void initModel() {
        Model.getInstance().setKurals(myDbHelper.getAllKurals());
        Model.getInstance().setAdhigarams(myDbHelper.getAllAdhigarams());
    }

    private void loadDB() {
        myDbHelper = new DBHelper(this);
        initModel();
    }

//    private void readJSON() {
//        try {
//
//            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
//            JSONArray kuralList = jsonObject.getJSONArray("kurals");
//            JSONArray adhigaramList = jsonObject.getJSONArray("chapters");
//            for (int i = 0; i < adhigaramList.length(); i++) {
//                String adhigaram = adhigaramList.getString(i);
//                int id = i + 1;
//                ContentValues values = new ContentValues();
//                values.put("Number", id);
//                values.put("Name", adhigaram);
//                myDbHelper.insertAdhigaramRecord(values);
//            }
//            for (int i = 0; i < kuralList.length(); i++) {
//                ContentValues values = new ContentValues();
//                JSONObject jo_inside = kuralList.getJSONObject(i);
//                JSONArray kural = jo_inside.getJSONArray("kural");
//                String kural_first = kural.getString(0);
//                String kural_second = kural.getString(1);
//                String kuralEntire = kural_first + "\n" + kural_second;
//                JSONObject explanation = jo_inside.getJSONObject("meaning");
//                String first_exp = explanation.getString("ta_mu_va");
//                String second_exp = explanation.getString("ta_salamon");
//                String third_exp = explanation.getString("en");
//                int beginIdx = first_exp.indexOf(':') + 1;
//                first_exp = first_exp.substring(beginIdx).trim();
//                beginIdx = second_exp.indexOf(':') + 1;
//                second_exp = second_exp.substring(beginIdx).trim();
//                beginIdx = third_exp.indexOf(':') + 1;
//                third_exp = third_exp.substring(beginIdx).trim();
//                values.put("id", i + 1);
//                values.put("kural", kuralEntire);
//                values.put("first_exp", first_exp);
//                values.put("second_exp", second_exp);
//                values.put("third_exp", third_exp);
//                myDbHelper.insertThirukkuralRecord(values);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = getAssets().open("thirukural.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }

    private void showAboutDialog() {
        View messageView = getLayoutInflater().inflate(R.layout.about_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_help);
        builder.setTitle("About");
        builder.setView(messageView);
        builder.create();
        builder.show();
    }

    @Override
    public void handlePositiveAction(String id, ArrayList<Integer> explanations) {
            shareKural(explanations);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
            return;
        }
        super.onBackPressed();
    }
}
