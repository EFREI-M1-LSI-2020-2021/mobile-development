package fr.efrei.badtracker.fragments.match;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.adapters.PlayerAdapter;
import fr.efrei.badtracker.adapters.SetAdapter;
import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.MatchLocation;

public class MatchFragment extends Fragment {

    private Match match;
    private Geocoder geocoder;

    private TextView title;
    private TextView date;
    private TextView location;
    private RecyclerView team1PlayersRecyclerView;
    private RecyclerView team2PlayersRecyclerView;
    private RecyclerView setsRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());

        MatchFragmentArgs args = MatchFragmentArgs.fromBundle(getArguments());
        match = args.getMatch();

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        title = view.findViewById(R.id.title);
        date = view.findViewById(R.id.date);
        location = view.findViewById(R.id.location);
        team1PlayersRecyclerView = view.findViewById(R.id.team1Players);
        team2PlayersRecyclerView = view.findViewById(R.id.team2Players);
        setsRecyclerView = view.findViewById(R.id.sets_list);

        title.setText(match.getName());
        date.setText(df.format(match.getDate()));

        MatchLocation matchLocation = match.getLocation();
        try {
            List<Address> addressList = geocoder.getFromLocation(matchLocation.getLatitude(),
                    matchLocation.getLongitude(), 1);
            if(addressList.size() > 0) {
                location.setText(addressList.get(0).getAddressLine(0));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        PlayerAdapter team1Adapter = new PlayerAdapter(this, match.getTeam1());
        PlayerAdapter team2Adapter = new PlayerAdapter(this, match.getTeam2());

        team1PlayersRecyclerView.setAdapter(team1Adapter);
        team1PlayersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        team2PlayersRecyclerView.setAdapter(team2Adapter);
        team2PlayersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        SetAdapter adapter = new SetAdapter(this, match.getSets());
        setsRecyclerView.setAdapter(adapter);
        setsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        return view;
    }
}