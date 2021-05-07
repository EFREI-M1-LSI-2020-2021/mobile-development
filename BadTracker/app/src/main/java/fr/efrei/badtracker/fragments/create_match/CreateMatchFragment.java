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

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchInfoFragment;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchInfoFragmentDirections;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchPhotoFragment;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchPhotoFragmentDirections;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchPlayersFragmentDirections;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchSetsFragmentDirections;
import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.MatchLocation;

public class CreateMatchFragment extends Fragment {

    private int index = 0;
    private final int max = 3;
    private boolean done = false;

    private NavHostFragment navHostFragment;
    private NavController navController;
    private ProgressBar progressBar;
    private Button nextButton;
    private Button backButton;

    private Match match = new Match();

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
        NavDirections direction = null;

        switch (index) {
            case 0:
                MatchInfoFragment matchInfoFragment = (MatchInfoFragment) fragment;
                if(!matchInfoFragment.validate()) {
                    return;
                }
                MatchInfoFragmentDirections.MatchInfoToPhoto infoToPhoto = MatchInfoFragmentDirections.MatchInfoToPhoto();
                infoToPhoto.setPhoto(match.getImage());
                direction = infoToPhoto;
                break;
            case 1:
                MatchPhotoFragment matchPhotoFragment = (MatchPhotoFragment) fragment;
                if(!matchPhotoFragment.validate()) {
                    return;
                }
                direction = MatchPhotoFragmentDirections.MatchPhotoToPlayers();
                break;
            case 2:
                direction = MatchPlayersFragmentDirections.MatchPlayersToSets();
                break;
        }

        index++;
        backButton.setVisibility(View.VISIBLE);

        if(index == max) {
            done = true;
            nextButton.setText(R.string.add);
        }
        progressBar.setProgress(progressBar.getProgress() + 25);

        navController.navigate(direction);
    }

    public void back(View view) {
        index--;
        if(index == 0) {
            backButton.setVisibility(View.GONE);
        }

        if(done) {
            nextButton.setText(R.string.next);
            done = false;
        }
        progressBar.setProgress(progressBar.getProgress() - 25);

        NavDirections direction = null;

        switch (index) {
            case 0:
                MatchPhotoFragmentDirections.MatchPhotoToInfo photoToInfo = MatchPhotoFragmentDirections.MatchPhotoToInfo();
                photoToInfo.setName(match.getName());
                photoToInfo.setLocation(match.getLocation());
                direction = photoToInfo;
                break;
            case 1:
                MatchPlayersFragmentDirections.MatchPlayersToPhoto playersToPhoto = MatchPlayersFragmentDirections.MatchPlayersToPhoto();
                playersToPhoto.setPhoto(match.getImage());
                direction = playersToPhoto;
                break;
            case 2:
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

    public void setImage(String image) {
        match.setImage(image);
    }
}