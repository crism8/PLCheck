package com.example.cristian.inzynierka;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * Created by Cristian on 2017-12-26.
 */

public class ListOfItemsDialog extends DialogFragment implements DialogInterface.OnClickListener {
    private static final String TAG = "ListOfItemsDialog";
    static int mResourceArray;
    static int mSelectedIndex;
    static View pressedButton;
    static OnDialogSelectorListener mDialogSelectorCallback;

    public interface OnDialogSelectorListener {
        public void onSelectedOption(int dialogId, View view);
    }

    public static ListOfItemsDialog newInstance(int res, int selected, View view) {
        final ListOfItemsDialog dialog  = new ListOfItemsDialog();
        mResourceArray = res;
        mSelectedIndex = selected;
        pressedButton = view;
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mDialogSelectorCallback = (OnDialogSelectorListener)activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDialogSelectorListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        builder.setTitle(R.string.chooseItemTitle);
        builder.setSingleChoiceItems(mResourceArray, mSelectedIndex, this);
        builder.setPositiveButton(R.string.ok, this);
        builder.setNegativeButton(R.string.cancel, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            case Dialog.BUTTON_NEGATIVE:
                dialog.cancel();
                break;

            case Dialog.BUTTON_POSITIVE:
                dialog.dismiss();
                // message selected value to registered calbacks
                mDialogSelectorCallback.onSelectedOption(mSelectedIndex, pressedButton);
                break;

            default: // choice selected click
                mSelectedIndex = which;
                break;
        }

    }
}
