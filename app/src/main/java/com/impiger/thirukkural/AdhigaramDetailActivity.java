package com.impiger.thirukkural;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.impiger.thirukkural.adapter.AdhigaramDetailAdapter;
import com.impiger.thirukkural.database.DBHelper;
import com.impiger.thirukkural.listeners.KuralListClickListener;
import com.impiger.thirukkural.model.Constants;
import com.impiger.thirukkural.model.Thirukkural;

import java.util.ArrayList;

public class AdhigaramDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private DBHelper myDbHelper;
    private FloatingActionButton actionButton;
    int[] primaryColors;
    int[] primaryDarkColors;
    boolean isFavorite;
    private int kuralStartNumber;
    private int kuralEndNumber;
    private String title;
    private int adhigaramId;
    private ArrayList<Thirukkural> kurals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adhigaram_description);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        actionButton = (FloatingActionButton) findViewById(R.id.favourite_button);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        primaryColors = getResources().getIntArray(R.array.primary_colors);
        primaryDarkColors = getResources().getIntArray(R.array.primary_dark_colors);

        mRecyclerView = (RecyclerView) findViewById(R.id.kural_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addOnItemTouchListener(new KuralListClickListener(this, new KuralListClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), KuralActivity.class);
                intent.putExtra(Constants.EXTRA_START_ID, (kuralStartNumber + position) - 1);
                intent.putExtra(Constants.EXTRA_ADHIGARAM_INDEX, adhigaramId);
                startActivity(intent);
            }
        }));
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                actionButton.startAnimation(
                        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_button));
                isFavorite = !isFavorite;
                ContentValues values = new ContentValues();
                values.put("Name", title);
                values.put("Number", adhigaramId);
                String toastText = getResources().getString(isFavorite ? R.string.favorite_add : R
                        .string.favorite_remove);
                if (isFavorite) {
                    myDbHelper.insertFavoriteRecord(values);

                    Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
                } else {
                    myDbHelper.deleteFavorite("" + adhigaramId);
                    Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
                }
                updateFavorite();
            }
        });

        adhigaramId = getIntent().getExtras().getInt(Constants.EXTRA_ADHIGARAM_INDEX);
        updateColors();
        kuralStartNumber = getIntent().getExtras().getInt(Constants.EXTRA_KURAL_START);
        kuralEndNumber = getIntent().getExtras().getInt(Constants.EXTRA_KURAL_END);
        title = getIntent().getExtras().getString(Constants.EXTRA_TITLE);
        getSupportActionBar().setTitle(title);

        loadDB();
        isFavorite = myDbHelper.isFavorite("" + adhigaramId);
        updateFavorite();
        kurals = myDbHelper.getKurals(kuralStartNumber, kuralEndNumber);

        AdhigaramDetailAdapter adapter = new AdhigaramDetailAdapter(this, kurals);
        mRecyclerView.setAdapter(adapter);
    }

    private void updateFavorite() {
        actionButton.setImageResource(isFavorite ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);
    }

    private void updateColors() {
        int colorIndex = adhigaramId;
        colorIndex %= 9;
        View view = findViewById(R.id.primary_color_view);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id
                .collapsing_toolbar);
        toolbarLayout.setContentScrimColor(primaryColors[colorIndex]);
        view.setBackgroundColor(primaryColors[colorIndex]);
        toolbar.setBackgroundColor(primaryColors[colorIndex]);
        actionButton.setBackgroundTintList(ColorStateList.valueOf(primaryColors[colorIndex]));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(primaryDarkColors[colorIndex]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adhigaram_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_adhigaram_share) {
            shareAdhigaram();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDB() {
        myDbHelper = new DBHelper(this);
    }

    private Intent getShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, getKurals());
        return intent;
    }

    private String getKurals() {
        StringBuilder builder = new StringBuilder();
        builder.append(title).append("\n");
        for (Thirukkural kural : kurals) {
            builder.append(kural);
            builder.append("\n\n");
        }
        return builder.toString();
    }

    private void shareAdhigaram() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, getKurals());
        startActivity(intent);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_through_right);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }
}
