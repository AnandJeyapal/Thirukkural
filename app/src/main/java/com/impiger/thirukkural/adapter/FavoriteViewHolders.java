package com.impiger.thirukkural.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.impiger.thirukkural.R;
import com.impiger.thirukkural.listeners.FavouriteClickListener;

/**
 * Created by anand on 16/03/16.
 */
public class FavoriteViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView largeText;
    public View itemView;
    public TextView description;
    private FavouriteClickListener listener;

    public FavoriteViewHolders(View itemView, FavouriteClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.itemView = itemView;
        this.listener = listener;
        largeText = (TextView) itemView.findViewById(R.id.large_description);
        description = (TextView) itemView.findViewById(R.id.description);
    }

    @Override
    public void onClick(View view) {
        listener.onFavoriteClick(getAdapterPosition());
    }

    public View getView() {
        return itemView;
    }
}