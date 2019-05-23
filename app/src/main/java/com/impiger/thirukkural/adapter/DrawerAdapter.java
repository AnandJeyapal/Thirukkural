package com.impiger.thirukkural.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.impiger.thirukkural.R;

/**
 * Created by anand on 30/11/15.
 */
public class DrawerAdapter extends BaseAdapter {

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    private static final int TYPE_ITEM = 1;
    private final Context context;

    private String mNavTitles[];
    private int mIcons[];

    public DrawerAdapter(Context context, String Titles[], int Icons[]) {
        mNavTitles = Titles;
        mIcons = Icons;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mNavTitles.length + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = null;
        if (position > 0) {
            rowView = inflater.inflate(R.layout.item_row, parent, false);
            TextView menuText = (TextView) rowView.findViewById(R.id.rowText);
            ImageView menuIcon = (ImageView) rowView.findViewById(R.id.rowIcon);
            menuText.setText(mNavTitles[position-1 ]);
            menuIcon.setImageResource(mIcons[position-1]);
        } else {
            rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
        }
        return rowView;
    }
}