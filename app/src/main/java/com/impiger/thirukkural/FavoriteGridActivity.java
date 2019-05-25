package com.impiger.thirukkural;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.impiger.thirukkural.adapter.FavoriteGridAdapter;
import com.impiger.thirukkural.adapter.SpacesItemDecoration;
import com.impiger.thirukkural.database.DBHelper;
import com.impiger.thirukkural.listeners.FavouriteClickListener;
import com.impiger.thirukkural.model.Adhigaram;
import com.impiger.thirukkural.model.Constants;
import com.impiger.thirukkural.model.Favorite;

import java.util.ArrayList;

public class FavoriteGridActivity extends AppCompatActivity implements FavouriteClickListener{
    private static final String TAG = AppCompatActivity.class.getSimpleName();
    private DBHelper myDbHelper;
    private Toolbar toolbar;
    private ArrayList<Favorite> favorites;
    private GridLayoutManager layoutManager;
    private RecyclerView favoritesRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_recycle_page);
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
        favoritesRecycleView = (RecyclerView) findViewById(R.id.favourite_recycler_view);
        layoutManager = new GridLayoutManager(this, 2);
        favoritesRecycleView.setLayoutManager(layoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        favoritesRecycleView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        loadDB();
        favorites = myDbHelper.getAllFavorites();
        FavoriteGridAdapter mAdapter = new FavoriteGridAdapter(this, favorites, this);
        favoritesRecycleView.setAdapter(mAdapter);
    }

    private void updateAdapter() {
        favorites = myDbHelper.getAllFavorites();
        FavoriteGridAdapter mAdapter = new FavoriteGridAdapter(this, favorites, this);
        favoritesRecycleView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void loadDB() {
        myDbHelper = new DBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAdapter();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onFavoriteClick(int position) {
        Favorite fav = favorites.get(position);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        Adhigaram adhigaram = dbHelper.getAdhigaramsByNumber(fav.getAdhigaramIdx() + 1);
        Intent intent = new Intent(getApplicationContext(), AdhigaramDetailActivity.class);
        intent.putExtra(Constants.EXTRA_KURAL_START, (adhigaram.getStartKural()));
        intent.putExtra(Constants.EXTRA_KURAL_END, (adhigaram.getEndKural()));
        intent.putExtra(Constants.EXTRA_ADHIGARAM_INDEX, fav.getAdhigaramIdx());
        intent.putExtra(Constants.EXTRA_TITLE, favorites.get(position).getAdhigaramName());
        startActivity(intent);
    }
}
