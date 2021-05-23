package fr.efrei.badtracker.fragments.create_match;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.List;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.Utils;
import fr.efrei.badtracker.api.MatchApi;
import fr.efrei.badtracker.api.dtos.CreateMatchDto;
import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.interfaces.IMatchDao;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchInfoFragment;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchInfoFragmentDirections;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchPhotoFragment;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchPhotoFragmentDirections;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchPlayersFragment;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchPlayersFragmentDirections;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchSetsFragment;
import fr.efrei.badtracker.fragments.create_match.fragments.MatchSetsFragmentDirections;
import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.MatchLocation;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Set;
import fr.efrei.badtracker.models.Sets;
import fr.efrei.badtracker.models.Team;
import fr.efrei.badtracker.services.ApiService;

public class CreateMatchFragment extends Fragment {
    private View view;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private ProgressBar progressBar;
    private Button nextButton;
    private Button backButton;
    private ApiService apiService;
    private MatchApi matchApi;

    private int index = 0;
    private final int max = 3;
    private boolean done = false;
    private Match match = new Match();
    private IMatchDao matchDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchDao = DbHelper.getInstance(this.getContext()).getDao(IMatchDao.class);
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", index);
        outState.putSerializable("match", match);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_match, container, false);

        apiService = ApiService.getInstance();
        matchApi = apiService.getMatchApi();
        navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());

        progressBar = view.findViewById(R.id.progressBar);
        nextButton = view.findViewById(R.id.next);
        backButton = view.findViewById(R.id.back);

        if(savedInstanceState != null) {
            index = savedInstanceState.getInt("index", 0);
            updateState(index);

            Object obj = savedInstanceState.getSerializable("match");
            if(obj != null) {
                match = (Match) obj;
            }
            else {
                match = new Match();
            }
        }

        MutableLiveData<Integer> indexData = NavHostFragment.findNavController(this).getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("index");
        indexData.observe(getViewLifecycleOwner(), this::updateState);

        MutableLiveData<Match> matchData = NavHostFragment.findNavController(this).getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("Match");
        matchData.observe(getViewLifecycleOwner(), match -> this.match = match);

        nextButton.setOnClickListener(this::next);
        backButton.setOnClickListener(this::back);

        return view;
    }

    private void updateState(int index) {
        this.index = index;
        done = index == max;

        if(index > 0) {
            backButton.setVisibility(View.VISIBLE);
            progressBar.setProgress((index + 1) * 25);
        }
        if(index == max) {
            done = true;
            nextButton.setText(R.string.add);
        }
    }

    public void next(View view) {

        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if(done) {
            MatchSetsFragment matchSetsFragment = (MatchSetsFragment) fragment;
            if(!matchSetsFragment.validate()) {
                return;
            }

            ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setTitle(R.string.create_new_match);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setIndeterminate(true);
            dialog.show();

            match.setDate(new Timestamp(System.currentTimeMillis()));
            matchDao.safeAdd(match);

            CreateMatchDto createMatchDto = new CreateMatchDto(match);

            apiService.execute(matchApi.addMatch(createMatchDto), response -> {
                dialog.dismiss();
                if(response.isSuccessful()) {
                    NavHostFragment.findNavController(this).popBackStack();
                }
                else {
                    Utils.showSnackbar(view, R.string.failed_save_online_match, -1, null);
                }
            });

            return;
        }
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
                MatchPhotoFragmentDirections.MatchPhotoToPlayers photoToPlayers = MatchPhotoFragmentDirections.MatchPhotoToPlayers();
                photoToPlayers.setTeam1((Team) match.getTeam1());
                photoToPlayers.setTeam2((Team) match.getTeam2());
                direction = photoToPlayers;
                break;
            case 2:
                MatchPlayersFragment matchPlayersFragment = (MatchPlayersFragment) fragment;
                if(!matchPlayersFragment.validate()) {
                    return;
                }
                MatchPlayersFragmentDirections.MatchPlayersToSets playersToSets = MatchPlayersFragmentDirections.MatchPlayersToSets();
                playersToSets.setSets((Sets) match.getSets());
                direction = playersToSets;
                break;
        }

        if(direction == null) {
            return;
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
        NavDirections direction = null;

        switch (index - 1) {
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
                MatchSetsFragmentDirections.MatchSetsToPlayers setsToPlayers = MatchSetsFragmentDirections.MatchSetsToPlayers();
                setsToPlayers.setTeam1((Team) match.getTeam1());
                setsToPlayers.setTeam2((Team) match.getTeam2());
                direction = setsToPlayers;
                break;
        }

        if(direction == null) {
            return;
        }

        index--;
        if(index == 0) {
            backButton.setVisibility(View.GONE);
        }

        if(done) {
            nextButton.setText(R.string.next);
            done = false;
        }
        progressBar.setProgress(progressBar.getProgress() - 25);

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

    public void setTeams(List<Player> team1, List<Player> team2) {
        match.setTeam1(team1);
        match.setTeam2(team2);
    }

    public void setSets(List<Set> sets) {
        match.setSets(sets);
    }

    public Bundle getState() {
        Bundle outState = new Bundle();
        outState.putInt("index", index);
        outState.putSerializable("match", match);
        return outState;
    }
}