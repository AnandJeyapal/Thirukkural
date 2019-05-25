package com.impiger.thirukkural.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.impiger.thirukkural.AdhigaramDetailActivity;
import com.impiger.thirukkural.FavoriteActivity;
import com.impiger.thirukkural.KuralActivity;
import com.impiger.thirukkural.R;
import com.impiger.thirukkural.adapter.AdhigaramDetailAdapter;
import com.impiger.thirukkural.adapter.FavoriteGridAdapter;
import com.impiger.thirukkural.adapter.SpacesItemDecoration;
import com.impiger.thirukkural.database.DBHelper;
import com.impiger.thirukkural.listeners.FavouriteClickListener;
import com.impiger.thirukkural.model.Adhigaram;
import com.impiger.thirukkural.model.Constants;
import com.impiger.thirukkural.model.Favorite;
import com.impiger.thirukkural.model.Thirukkural;

import java.util.ArrayList;

public class FavoriteKuralFragment extends Fragment implements FavouriteClickListener {
    private DBHelper myDbHelper;
    private ArrayList<Thirukkural> favorites;
    private LinearLayoutManager layoutManager;
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
        layoutManager = new LinearLayoutManager(getContext());
        favoritesRecycleView.setLayoutManager(layoutManager);
        loadDB();
        favorites = myDbHelper.getAllKuralFavorites();
        AdhigaramDetailAdapter mAdapter = new AdhigaramDetailAdapter(getContext(), favorites, this);
        favoritesRecycleView.setAdapter(mAdapter);
        favoritesRecycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }

    public FavoriteActivity getFavoriteActivity() {
        return (FavoriteActivity) getActivity();
    }

    private void loadDB() {
        myDbHelper = new DBHelper(getContext());
    }

    @Override
    public void onFavoriteClick(int position) {
        Thirukkural fav = favorites.get(position);
        Intent intent = new Intent(getContext(), KuralActivity.class);
        intent.putExtra(Constants.EXTRA_START_ID, fav.getId() - 1);
        getFavoriteActivity().startActivity(intent);
    }
}
