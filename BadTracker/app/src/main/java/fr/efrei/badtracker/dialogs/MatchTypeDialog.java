package fr.efrei.badtracker.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import fr.efrei.badtracker.MatchType;
import fr.efrei.badtracker.R;

public class MatchTypeDialog extends DialogFragment {

    private static final CharSequence[] types = new CharSequence[]{
            MatchType.SIMPLE,
            MatchType.DOUBLE
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.dialog_title_match_type))
                .setItems(types, (dialog, index) -> {
                    NavDirections navDirections = MatchTypeDialogDirections.
                            actionMatchTypeDialogToCreateMatchFragment(types[index].toString());
                    NavHostFragment.findNavController(this).navigate(navDirections);
                });
        return builder.create();

    }
}
