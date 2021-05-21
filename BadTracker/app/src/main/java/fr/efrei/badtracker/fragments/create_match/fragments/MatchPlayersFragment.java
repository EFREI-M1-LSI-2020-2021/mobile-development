package fr.efrei.badtracker.fragments.create_match.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.fragments.create_match.CreateMatchFragment;
import fr.efrei.badtracker.fragments.create_match.adapters.PlayerAdapter;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Sex;
import fr.efrei.badtracker.models.Team;

public class MatchPlayersFragment extends Fragment {

    private CreateMatchFragment createMatchFragment;
    private Team team1 = new Team();
    private Team team2 = new Team();
    private boolean addInTeam1 = false;
    private boolean addInTeam2 = false;

    private FloatingActionButton addPlayerTeam1Button;
    private FloatingActionButton addPlayerTeam2Button;
    private RecyclerView team1PlayersRecyclerView;
    private RecyclerView team2PlayersRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_match_players, container, false);

        setRetainInstance(true);

        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        createMatchFragment = (CreateMatchFragment) navHostFragment.getParentFragment();

        addPlayerTeam1Button = view.findViewById(R.id.addPlayerTeam1);
        addPlayerTeam2Button = view.findViewById(R.id.addPlayerTeam2);

        MatchPlayersFragmentArgs args = MatchPlayersFragmentArgs.fromBundle(getArguments());
        Team team1 = args.getTeam1();
        if(team1 != null) {
            this.team1 = team1;
            if(this.team1.size() >= 2) {
                addPlayerTeam1Button.setVisibility(View.GONE);
            }
        }

        Team team2 = args.getTeam2();
        if(team2 != null) {
            this.team2 = team2;
            if(this.team2.size() >= 2) {
                addPlayerTeam2Button.setVisibility(View.GONE);
            }
        }

        addPlayerTeam1Button.setOnClickListener(v -> {
            addInTeam1 = true;
            NavHostFragment.findNavController(createMatchFragment).navigate(R.id.action_CreateMatch_to_createPlayerFragment);
        });

        addPlayerTeam2Button.setOnClickListener(v -> {
            addInTeam2 = true;
            NavHostFragment.findNavController(createMatchFragment).navigate(R.id.action_CreateMatch_to_createPlayerFragment);
        });

        team1PlayersRecyclerView = view.findViewById(R.id.team1Players);
        team2PlayersRecyclerView = view.findViewById(R.id.team2Players);

        PlayerAdapter team1Adapter = new PlayerAdapter(this, this.team1);
        PlayerAdapter team2Adapter = new PlayerAdapter(this, this.team2);

        team1PlayersRecyclerView.setAdapter(team1Adapter);
        team1PlayersRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        team2PlayersRecyclerView.setAdapter(team2Adapter);
        team2PlayersRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MutableLiveData<Player> liveData = NavHostFragment.findNavController(createMatchFragment).getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("player");
        liveData.observe(getViewLifecycleOwner(), player -> {

            if(addInTeam1) {
                this.team1.add(player);
                team1Adapter.notifyDataSetChanged();
                if(this.team1.size() >= 2) {
                    addPlayerTeam1Button.setVisibility(View.GONE);
                }
            }
            else if(addInTeam2) {
                this.team2.add(player);
                team2Adapter.notifyDataSetChanged();
                if(this.team2.size() >= 2) {
                    addPlayerTeam2Button.setVisibility(View.GONE);
                }
            }
            addInTeam1 = false;
            addInTeam2 = false;
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    public boolean validate() {
        return team1.size() > 0 && team2.size() > 0 && team1.size() == team2.size();
    }

    private void save() {
        createMatchFragment.setTeams(team1, team2);
    }
}