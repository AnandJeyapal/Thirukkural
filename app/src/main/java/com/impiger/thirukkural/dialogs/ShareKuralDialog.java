package com.impiger.thirukkural.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.impiger.thirukkural.R;
import com.impiger.thirukkural.listeners.ShareKuralDialogListener;

import java.util.ArrayList;

/**
 * Created by anand on 21/12/15.
 */
public class ShareKuralDialog extends DialogFragment {
    private ArrayList mSelectedItems;
    private String id;
    private ShareKuralDialogListener dialogActionHandler;

    public ShareKuralDialog(String id, ShareKuralDialogListener dialogActionHandler) {
        this.id = id;
        this.dialogActionHandler = dialogActionHandler;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSelectedItems = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.share_dlg_title)
                .setMultiChoiceItems(R.array.toppings, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    mSelectedItems.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                        // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        int[] arr = {0, 1, 2};
                        dialogActionHandler.handlePositiveAction(id, mSelectedItems);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        dismiss();
                    }
                });

        return builder.create();


    }
}
