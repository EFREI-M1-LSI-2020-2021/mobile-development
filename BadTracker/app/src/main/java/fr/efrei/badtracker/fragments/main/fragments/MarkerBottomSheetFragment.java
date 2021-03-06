package fr.efrei.badtracker.fragments.main.fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.util.Locale;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.fragments.main.MainFragmentDirections;
import fr.efrei.badtracker.models.Match;

public class MarkerBottomSheetFragment extends BottomSheetDialogFragment {

    private final Match match;

    public MarkerBottomSheetFragment(Match match) {
        this.match = match;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marker_bottom_sheet, container, false);

        TextView name = view.findViewById(R.id.name);
        TextView team1 = view.findViewById(R.id.team1);
        TextView team2 = view.findViewById(R.id.team2);
        TextView date = view.findViewById(R.id.date);
        Button open = view.findViewById(R.id.open);


        name.setText(match.getName());
        team1.setText(match.getTeamName(match.getTeam1()));
        team2.setText(match.getTeamName(match.getTeam2()));
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        date.setText(df.format(match.getDate()));

        if(match.isteam1Winner(match)) {
            team1.setTypeface(team1.getTypeface(), Typeface.BOLD);
        }
        else {
            team2.setTypeface(team2.getTypeface(), Typeface.BOLD);
        }

        open.setOnClickListener(this::openMatch);

        return view;
    }

    private void openMatch(View view) {
        this.dismiss();
        NavDirections navDirections = MainFragmentDirections.MainToMatch(match);
        NavHostFragment.findNavController(this).navigate(navDirections);
    }
}