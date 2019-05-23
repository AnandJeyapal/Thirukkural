package com.impiger.thirukkural.fragment;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.impiger.thirukkural.AdhigaramSplash;
import com.impiger.thirukkural.R;
import com.impiger.thirukkural.Utils;
import com.impiger.thirukkural.adapter.AdhigaramAdapter;
import com.impiger.thirukkural.database.DBHelper;
import com.impiger.thirukkural.model.Adhigaram;
import com.impiger.thirukkural.model.Constants;

import java.util.ArrayList;

/**
 * Created by anand on 17/12/15.
 */
public class AramFragment extends Fragment {
    private DBHelper myDbHelper;
    private ListView kuralList;
    private View rootView;
    private String partName;
    private ArrayList<Adhigaram> adhigarams;
    private AdhigaramAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partName = getArguments().getString(Constants.PART_NAME);
        loadDB();
        adhigarams = myDbHelper.getAdhigaramsByPart(partName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.adhigaram_list_page, container, false);
        kuralList = (ListView) rootView.findViewById(R.id.adhigaram_list);
        kuralList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int adhigaramId = ((Integer) view.getTag()) - 1;
                Adhigaram adhigaram = adhigarams.get(position);
                Intent intent = new Intent(getActivity(), AdhigaramSplash.class);
                intent.putExtra(Constants.EXTRA_KURAL_START, adhigaram.getStartKural());
                intent.putExtra(Constants.EXTRA_KURAL_END, adhigaram.getEndKural());
                intent.putExtra(Constants.EXTRA_START_ID, (adhigaramId * 10) + 1);
                intent.putExtra(Constants.EXTRA_ADHIGARAM_INDEX, adhigaramId);
                intent.putExtra(Constants.EXTRA_TITLE, adhigaram.getAdhigaramName());
                getActivity().startActivity(intent);
            }
        });
        adapter = new AdhigaramAdapter(getContext(), adhigarams);
        kuralList.setAdapter(adapter);
        return rootView;
    }

    public void doSearch(String adhigaramNumber) {
        ArrayList<Adhigaram> matchedAdhigarams = getMatchedAdhigarams(adhigaramNumber);
        adapter = new AdhigaramAdapter(getContext(), matchedAdhigarams);
        kuralList.setAdapter(adapter);
    }

    private ArrayList<Adhigaram> getMatchedAdhigarams(String text) {
        ArrayList<Adhigaram> matchedAdhigarams = new ArrayList();
        for (Adhigaram adhigaram : adhigarams) {
            if (String.valueOf(adhigaram.getAdhigaramNumber()).startsWith(text)) {
                matchedAdhigarams.add(adhigaram);
            }
        }
        return matchedAdhigarams;
    }

    public void resetScreen() {
        adapter = new AdhigaramAdapter(getContext(), adhigarams);
        kuralList.setAdapter(adapter);
    }
    private void loadDB() {
        myDbHelper = new DBHelper(getActivity());
    }
}
