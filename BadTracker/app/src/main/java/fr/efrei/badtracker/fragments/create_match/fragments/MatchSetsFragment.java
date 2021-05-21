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
import fr.efrei.badtracker.fragments.create_match.adapters.SetAdapter;
import fr.efrei.badtracker.models.Set;
import fr.efrei.badtracker.models.Sets;

public class MatchSetsFragment extends Fragment {

    private CreateMatchFragment createMatchFragment;
    private Sets sets = new Sets();

    private FloatingActionButton addSet;
    private RecyclerView setsRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_match_sets, container, false);

        setRetainInstance(true);

        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        createMatchFragment = (CreateMatchFragment) navHostFragment.getParentFragment();

        addSet = view.findViewById(R.id.addSet);

        MatchSetsFragmentArgs args = MatchSetsFragmentArgs.fromBundle(getArguments());
        Sets sets = args.getSets();
        if(sets != null) {
            this.sets = sets;
            if(this.sets.size() >= 3) {
                addSet.setVisibility(View.GONE);
            }
        }

        addSet.setOnClickListener(v -> {
            NavHostFragment.findNavController(createMatchFragment).navigate(R.id.CreateMatchToCreateSet, createMatchFragment.getState());
        });

        setsRecyclerView = view.findViewById(R.id.sets);

        SetAdapter adapter = new SetAdapter(this, this.sets);
        setsRecyclerView.setAdapter(adapter);
        setsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MutableLiveData<Set> liveData = NavHostFragment.findNavController(createMatchFragment).getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("set");
        liveData.observe(getViewLifecycleOwner(), set -> {
            this.sets.add(set);
            adapter.notifyDataSetChanged();
            if(this.sets.size() >= 3) {
                addSet.setVisibility(View.GONE);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    public boolean validate() {
        return sets.size() >= 2;
    }

    private void save() {
        createMatchFragment.setSets(sets);
    }
}