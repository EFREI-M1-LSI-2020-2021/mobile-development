package fr.efrei.badtracker.fragments.create_match;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.R;

public class CreateMatchFragment extends Fragment {

    private int index = 0;
    private final int max = 2;
    private boolean done = false;

    private NavController navController;
    private ProgressBar progressBar;
    private Button nextButton;
    private Button backButton;

    private final List<Integer> nextFragments = new ArrayList<Integer>(){{
        add(R.id.action_matchInfoFragment_to_matchPlayersFragment);
        add(R.id.action_matchPlayersFragment_to_matchSetsFragment);
    }};

    private final List<Integer> backFragments = new ArrayList<Integer>(){{
        add(R.id.action_matchPlayersFragment_to_matchInfoFragment);
        add(R.id.action_matchSetsFragment_to_matchPlayersFragment);
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

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment);
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

        navController.navigate(backFragments.get(index));
    }
}