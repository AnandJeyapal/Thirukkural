package com.impiger.thirukkural.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.impiger.thirukkural.R;
import com.impiger.thirukkural.listeners.FavouriteClickListener;
import com.impiger.thirukkural.model.Thirukkural;

import java.util.List;

/**
 * Created by anand on 09/12/15.
 */
public class AdhigaramDetailAdapter extends RecyclerView.Adapter<AdhigaramDetailAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<Thirukkural> mData;
    private FavouriteClickListener favouriteClickListener;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.kural_text_view);
        }
    }

    public AdhigaramDetailAdapter(Context context, List<Thirukkural> data, @Nullable FavouriteClickListener listener) {
        mContext = context;
        mData = data;
        this.favouriteClickListener = listener;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.kural_list_view, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.title.setText(mData.get(position).getKural());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(favouriteClickListener != null) {
                   favouriteClickListener.onFavoriteClick(position);
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}