package com.impiger.thirukkural.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.impiger.thirukkural.R;
import com.impiger.thirukkural.model.Adhigaram;

import java.util.ArrayList;

public class AdhigaramAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Adhigaram> adhigarams;

    public AdhigaramAdapter(Context context, ArrayList<Adhigaram> adhigarams) {
        this.context = context;
        this.adhigarams = adhigarams;
    }

    @Override
    public int getCount() {
        return adhigarams.size();
    }

    @Override
    public Object getItem(int position) {
        return adhigarams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adhigaram_row, parent, false);
        rowView.setTag(adhigarams.get(position).getAdhigaramNumber());
        TextView adhigaramNumber = (TextView) rowView.findViewById(R.id.adhigaram_number);
        TextView adhigaramDescription = (TextView) rowView.findViewById(R.id.adhigaram_description);
        adhigaramNumber.setText("" + (adhigarams.get(position).getAdhigaramNumber()));
        adhigaramDescription.setText(adhigarams.get(position).getAdhigaramName());
        adhigaramNumber.setTextColor(Color.BLACK);
        return rowView;
    }
}
