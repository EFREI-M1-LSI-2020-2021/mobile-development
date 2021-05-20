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
    private ApiService apiService;
    private MatchApi matchApi;
    private List<Match> matches = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last_matches, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        apiService = ApiService.getInstance();
        matchApi = apiService.getMatchApi();

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            matches.clear();
            matches.addAll(getMatches());
            swipeRefreshLayout.setRefreshing(false);
            adapter.notifyDataSetChanged();
        });

        matches = getMatches();
        adapter = new MatchAdapter(getParentFragment(), matches);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private List<Match> getMatches(){
        List<Match> returnMatches = new ArrayList<>();

        Response<List<MatchDto>> response = apiService.execute(matchApi.getMatches());
        if(response.isSuccessful()) {
            List<MatchDto> matches = response.body();
            for(MatchDto matchDto : matches) {
                Match match = matchDto.toMatch();
                returnMatches.add(match);
            }
        }

        return returnMatches;
    }
}