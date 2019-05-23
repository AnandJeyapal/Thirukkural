package com.impiger.thirukkural.listeners;

import android.content.DialogInterface;

import java.util.ArrayList;

/**
 * Created by anand on 21/12/15.
 */
public interface ShareKuralDialogListener {
    public void handlePositiveAction(String id, ArrayList<Integer> selectedExplanations);
}