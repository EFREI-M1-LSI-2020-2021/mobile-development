package fr.efrei.badtracker;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utils {

    public static void showSnackbar(View view, final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view,
                view.getContext().getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE);
        if(actionStringId != -1) {
            snackbar = snackbar.setAction(view.getContext().getString(actionStringId), listener);
        }
        snackbar.show();
    }
}
