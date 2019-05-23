package com.impiger.thirukkural.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by anand on 02/12/15.
 */
public class Thirukkural {
    private int id;
    private String kural;
    private ArrayList<String> explanations = new ArrayList();
    private ArrayList<String> explanationDescs = new ArrayList();

    public String getKural() {
        return kural;
    }

    public void setKural(String kural) {
        this.kural = kural;
    }

    public Thirukkural() {
        explanationDescs.add("மு.வ உரை");
        explanationDescs.add("சாலமன் பாப்பையா உரை");
        explanationDescs.add("English");
    }

    public String getThirdExplanation() {
        return explanations.get(2);
    }

    public void setThirdExplanation(String thirdExplanation) {
        explanations.add(thirdExplanation);
    }

    public String getFirstExplanation() {
        return explanations.get(0);
    }

    public void setFirstExplanation(String firstExplanation) {
        explanations.add(firstExplanation);
    }

    public String getSecondExplanation() {
        return explanations.get(1);
    }

    public void setSecondExplanation(String secondExplanation) {
        explanations.add(secondExplanation);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return kural;
    }

    public String getExplanations(ArrayList<Integer> ids) {
        Collections.sort(ids);
        StringBuilder explanation = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            int index = ids.get(i);
            explanation.append("\n").append(explanationDescs.get(index)).append(": ").append
                    (explanations.get(0));
        }
        return explanation.toString();
    }

}
