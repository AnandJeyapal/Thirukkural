package com.impiger.thirukkural.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.impiger.thirukkural.R;
import com.impiger.thirukkural.model.Constants;
import com.impiger.thirukkural.model.Thirukkural;

/**
 * Created by anand on 17/11/15.
 */
public class ThirukkuralPage {
    private final int kuralNo;
    private View view;
    private Thirukkural question;
    private Context context;
    private TextView kuralView;
    private TextView firstExplanation;
    private TextView secondExplanation;
    private TextView thirdExplanation;
    private TextView kuralNumber;

    public ThirukkuralPage(Context context, Thirukkural question, int kuralNumber) {
        this.question = question;
        this.context = context;
        this.kuralNo = kuralNumber;
        initViews();
    }

    private void initViews(){
        view = LayoutInflater.from(context).inflate(R.layout.thirukkural_page, null);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.FONT_PATH);
        // Applying font
        kuralView = (TextView) view.findViewById(R.id.kural_view);
        kuralView.setTypeface(tf);
        firstExplanation = (TextView) view.findViewById(R.id.first_exp_view);
        secondExplanation = (TextView) view.findViewById(R.id.second_exp_view);
        thirdExplanation = (TextView) view.findViewById(R.id.third_exp_view);
        kuralNumber = (TextView) view.findViewById(R.id.kural_number);
        setData();
    }

    private void setData(){
        kuralView.setText(question.getKural());
        firstExplanation.setText(question.getFirstExplanation());
        secondExplanation.setText(question.getSecondExplanation());
        thirdExplanation.setText(question.getThirdExplanation());
        kuralNumber.setText(String.valueOf(kuralNo));
    }

    public View getView() {
        return view;
    }

}
