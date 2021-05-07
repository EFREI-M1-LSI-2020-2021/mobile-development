package fr.efrei.badtracker.fragments.create_match.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.fragments.create_match.CreateMatchFragment;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Team;

public class MatchPlayersFragment extends Fragment {

    private CreateMatchFragment createMatchFragment;
    private Team team1 = new Team();
    private Team team2 = new Team();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_match_players, container, false);

        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        createMatchFragment = (CreateMatchFragment) navHostFragment.getParentFragment();

        MatchPlayersFragmentArgs args = MatchPlayersFragmentArgs.fromBundle(getArguments());
        Team team1 = args.getTeam1();
        if(team1 != null) {
            this.team1 = team1;
        }

        Team team2 = args.getTeam2();
        if(team2 != null) {
            this.team2 = team2;
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    public boolean validate() {
        return team1.size() > 0 && team2.size() > 0;
    }

    private void save() {
    }
}