package com.impiger.thirukkural.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.impiger.thirukkural.AdhigaramDetailActivity;
import com.impiger.thirukkural.FavoriteActivity;
import com.impiger.thirukkural.R;
import com.impiger.thirukkural.adapter.FavoriteGridAdapter;
import com.impiger.thirukkural.adapter.SpacesItemDecoration;
import com.impiger.thirukkural.database.DBHelper;
import com.impiger.thirukkural.listeners.FavouriteClickListener;
import com.impiger.thirukkural.model.Adhigaram;
import com.impiger.thirukkural.model.Constants;
import com.impiger.thirukkural.model.Favorite;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements FavouriteClickListener {

    private DBHelper myDbHelper;
    private ArrayList<Favorite> favorites;
    private GridLayoutManager layoutManager;
    private RecyclerView favoritesRecycleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_adhigarams, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritesRecycleView = (RecyclerView) view.findViewById(R.id.favourite_recycler_view);
        layoutManager = new GridLayoutManager(getContext(), 2);
        favoritesRecycleView.setLayoutManager(layoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        favoritesRecycleView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        loadDB();
        favorites = myDbHelper.getAllFavorites();
        FavoriteGridAdapter mAdapter = new FavoriteGridAdapter(getContext(), favorites, this);
        favoritesRecycleView.setAdapter(mAdapter);
    }

    public FavoriteActivity getFavoriteActivity() {
        return (FavoriteActivity) getActivity();
    }

    private void loadDB() {
        myDbHelper = new DBHelper(getContext());
    }

    @Override
    public void onFavoriteClick(int position) {
        Favorite fav = favorites.get(position);
        DBHelper dbHelper = new DBHelper(getContext());
        Adhigaram adhigaram = dbHelper.getAdhigaramsByNumber(fav.getAdhigaramIdx() + 1);
        Intent intent = new Intent(getContext(), AdhigaramDetailActivity.class);
        intent.putExtra(Constants.EXTRA_KURAL_START, (adhigaram.getStartKural()));
        intent.putExtra(Constants.EXTRA_KURAL_END, (adhigaram.getEndKural()));
        intent.putExtra(Constants.EXTRA_ADHIGARAM_INDEX, fav.getAdhigaramIdx());
        intent.putExtra(Constants.EXTRA_TITLE, favorites.get(position).getAdhigaramName());
        getFavoriteActivity().startActivity(intent);
    }
}
