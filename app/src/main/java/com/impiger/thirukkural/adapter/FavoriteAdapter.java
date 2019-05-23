package com.impiger.thirukkural.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.impiger.thirukkural.R;
import com.impiger.thirukkural.Utils;
import com.impiger.thirukkural.dynamicgrid.BaseDynamicGridAdapter;
import com.impiger.thirukkural.dynamicgrid.DynamicGridUtils;
import com.impiger.thirukkural.model.Favorite;

import java.util.List;

public class FavoriteAdapter extends BaseDynamicGridAdapter {

    private Context context;
    private List<Favorite> favorites;

    public FavoriteAdapter(Context context, List<Favorite> favorites) {
        super(context, favorites, 2);
        this.context = context;
        this.favorites = favorites;
    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FavoriteViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.favorite_item, null);
            holder = new FavoriteViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (FavoriteViewHolder) convertView.getTag();
        }
        holder.build(favorites.get(position).getLargeDesc(), favorites.get(position).getAdhigaramName());
        int bgColor = Utils.getColorCode(context, favorites.get(position).getAdhigaramIdx());
        convertView.setBackgroundColor(bgColor);
        return convertView;
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(R.layout.favorite_item, parent, false);
//        int bgColor = Utils.getColorCode(context, favorites.get(position).getAdhigaramIdx());
//        rowView.setBackgroundColor(bgColor);
//        TextView largeText = (TextView) rowView.findViewById(R.id.large_description);
//        TextView description = (TextView) rowView.findViewById(R.id.description);
//        largeText.setText(favorites.get(position).getLargeDesc());
//        description.setText(favorites.get(position).getAdhigaramName());
//        rowView.setTag(position);
//        return rowView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void reorderItems(int originalPosition, int newPosition) {
        super.reorderItems(originalPosition, newPosition);
        if (newPosition < getCount()) {
            DynamicGridUtils.reorder(favorites, originalPosition, newPosition);
            notifyDataSetChanged();
        }
    }
    private class FavoriteViewHolder {
        private TextView largeText;
        private TextView description;

        private FavoriteViewHolder(View view) {
            largeText = (TextView) view.findViewById(R.id.large_description);
            description = (TextView) view.findViewById(R.id.description);
        }

        void build(String title, String desc) {
            largeText.setText(title);
            description.setText(desc);
        }
    }

}
