package com.impiger.thirukkural.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.impiger.thirukkural.model.Thirukkural;

import java.util.ArrayList;

/**
 * Created by anand on 17/11/15.
 */
public class ThirukkuralPageAdapter extends PagerAdapter {

    private ArrayList<Thirukkural> questions;
    private Context context;
    public ThirukkuralPageAdapter(Context context, ArrayList<Thirukkural> questions) {
        this.context = context;
        this.questions = questions;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ThirukkuralPage questionPage = new ThirukkuralPage(context, questions.get(position),
                position+1);
        View view = questionPage.getView();
        container.addView(view);
        return questionPage;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((ThirukkuralPage) object).getView());
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ThirukkuralPage) object).getView();
    }
}
