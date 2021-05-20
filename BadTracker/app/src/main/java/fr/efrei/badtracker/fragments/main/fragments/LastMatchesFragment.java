package fr.efrei.badtracker.fragments.main.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.api.ApiTask;
import fr.efrei.badtracker.api.MatchApi;
import fr.efrei.badtracker.api.dtos.MatchDto;
import fr.efrei.badtracker.fragments.main.adapters.MatchAdapter;
import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.MatchLocation;
import fr.efrei.badtracker.services.ApiService;
import retrofit2.Response;

public class LastMatchesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Match> matchs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last_matches, container, false);

        adapter = new MatchAdapter(getParentFragment(), matchs);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void setupMatches(){

    }
}