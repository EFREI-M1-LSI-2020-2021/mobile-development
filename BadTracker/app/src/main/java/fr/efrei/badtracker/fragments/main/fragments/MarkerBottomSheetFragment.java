package fr.efrei.badtracker.fragments.main.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.api.dtos.MatchDto;
import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.Player;

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
        Button open = view.findViewById(R.id.open);

        name.setText(match.getName());
        team1.setText(getTeamName(match.getTeam1()));
        team2.setText(getTeamName(match.getTeam2()));

        open.setOnClickListener(this::openMatch);

        return view;
    }

    private String getTeamName(List<Player> players) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            stringBuilder.append(player.getName()).append(" ").append(player.getFirstName());
            if(i + 1 < players.size()) {
                stringBuilder.append(" - ");
            }
        }

        return stringBuilder.toString();
    }

    private void openMatch(View view) {
        this.dismiss();
        NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_matchFragment);
    }
}