package fr.efrei.badtracker.fragments.create_match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchInfoFragment;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchPlayersFragmentDirections;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchSetsFragmentDirections;
import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.MatchLocation;

public class CreateMatchFragment extends Fragment {

    private int index = 0;
    private final int max = 2;
    private boolean done = false;

    private NavHostFragment navHostFragment;
    private NavController navController;
    private ProgressBar progressBar;
    private Button nextButton;
    private Button backButton;

    private Match match = new Match();

    private final List<Integer> nextFragments = new ArrayList<Integer>(){{
        add(R.id.MatchInfoToPlayers);
        add(R.id.MatchPlayersToSets);
    }};

    private final List<Integer> backFragments = new ArrayList<Integer>(){{
        add(R.id.MatchPlayersToInfo);
        add(R.id.MatchSetsToPlayers);
    }};

    public CreateMatchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_match, container, false);

        navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());

        progressBar = view.findViewById(R.id.progressBar);
        nextButton = view.findViewById(R.id.next);
        backButton = view.findViewById(R.id.back);

        nextButton.setOnClickListener(this::next);
        backButton.setOnClickListener(this::back);

        return view;
    }

    public void next(View view) {

        if(done) {
            // save match
            NavHostFragment.findNavController(this).popBackStack();
            return;
        }

        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        switch (index) {
            case 0:
                MatchInfoFragment matchInfoFragment = (MatchInfoFragment) fragment;
                if(!matchInfoFragment.validate()) {
                    return;
                }
                break;
            case 1:
                break;
            case 2:
                break;
        }

        index++;
        backButton.setVisibility(View.VISIBLE);

        if(index == max) {
            done = true;
            nextButton.setText(R.string.add);
            progressBar.setProgress(100);
        }
        else {
            progressBar.setProgress(progressBar.getProgress() + 33);
        }

        navController.navigate(nextFragments.get(index - 1));
    }

    public void back(View view) {
        index--;
        if(index == 0) {
            backButton.setVisibility(View.GONE);
        }

        if(done) {
            nextButton.setText(R.string.next);
            done = false;
            progressBar.setProgress(99 - 33);
        }
        else {
            progressBar.setProgress(progressBar.getProgress() - 33);
        }

        NavDirections direction = null;

        switch (index) {
            case 0:
                MatchPlayersFragmentDirections.MatchPlayersToInfo args = MatchPlayersFragmentDirections
                        .MatchPlayersToInfo();
                args.setName(match.getName());
                args.setLocation(match.getLocation());
                direction = args;
                break;
            case 1:
                direction = MatchSetsFragmentDirections.MatchSetsToPlayers();
                break;
        }

        navController.navigate(direction);
    }

    public void setMatchName(String name) {
        match.setName(name);
    }

    public void setMatchLocation(MatchLocation matchLocation) {
        match.setLocation(matchLocation);
    }
}