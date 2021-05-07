package fr.efrei.badtracker;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utils {

    public static void showSnackbar(View view, final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(view,
                view.getContext().getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(view.getContext().getString(actionStringId), listener).show();
    }
}
