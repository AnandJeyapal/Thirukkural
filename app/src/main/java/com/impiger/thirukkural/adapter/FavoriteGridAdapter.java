package com.impiger.thirukkural.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.impiger.thirukkural.R;
import com.impiger.thirukkural.Utils;
import com.impiger.thirukkural.listeners.FavouriteClickListener;
import com.impiger.thirukkural.model.Favorite;

import java.util.List;


public class FavoriteGridAdapter extends RecyclerView.Adapter<FavoriteViewHolders> {

    private List<Favorite> favorites;
    private Context context;
    private FavouriteClickListener listener;

    public FavoriteGridAdapter(Context context, List<Favorite> itemList, FavouriteClickListener listener) {
        this.favorites = itemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public FavoriteViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, null);
        FavoriteViewHolders rcv = new FavoriteViewHolders(layoutView, listener);
        return rcv;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolders holder, int position) {
        holder.largeText.setText(favorites.get(position).getLargeDesc());
        holder.description.setText(favorites.get(position).getAdhigaramName());
        int bgColor = Utils.getColorCode(context, favorites.get(position).getAdhigaramIdx());
        holder.itemView.setBackgroundColor(bgColor);
    }

    @Override
    public int getItemCount() {
        return this.favorites.size();
    }
}

