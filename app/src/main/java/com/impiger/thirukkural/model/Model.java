package com.impiger.thirukkural.model;

import java.util.ArrayList;

/**
 * Created by anand on 26/02/16.
 */
public class Model {

    private static Model instance;
    private ArrayList<Adhigaram> adhigarams;
    private ArrayList<Thirukkural> kurals;

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public void setAdhigarams(ArrayList<Adhigaram> adhigarams) {
        this.adhigarams = adhigarams;
    }

    public void setKurals(ArrayList<Thirukkural> kurals) {
        this.kurals = kurals;
    }

    public ArrayList<Adhigaram> getAdhigarams() {
        return adhigarams;
    }

    public ArrayList<String> getAramAdhigarams() {
        return subArrarList(Constants.ARAM_PART_START, Constants.PORUL_PART_START);
    }

    public ArrayList<String> getPorulAdhigarams() {
        return subArrarList(Constants.PORUL_PART_START, Constants.INBAM_PART_START);
    }

    public ArrayList<String> getInbamAdhigarams() {
        return subArrarList(Constants.INBAM_PART_START, 133);
    }

    private ArrayList<String> subArrarList(int start, int end) {
        ArrayList<String> sub = new ArrayList();
        for (int i = start; i < end; i++) {
            sub.add(adhigarams.get(i).getAdhigaramName());
        }
        return sub;
    }

    public ArrayList<Thirukkural> getKurals() {
        return kurals;
    }

    public Thirukkural getKural(int position) {
        return kurals.get(position);
    }

    public Adhigaram getAdhigaram(int adhigaramIndex) {
        return adhigarams.get(adhigaramIndex);
    }
}
