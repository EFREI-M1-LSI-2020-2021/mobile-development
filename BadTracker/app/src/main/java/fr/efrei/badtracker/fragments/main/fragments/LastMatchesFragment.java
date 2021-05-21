package fr.efrei.badtracker.fragments.main.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.MatchDao;
import fr.efrei.badtracker.database.daos.interfaces.IMatchDao;
import fr.efrei.badtracker.fragments.main.adapters.MatchAdapter;
import fr.efrei.badtracker.models.Match;

public class LastMatchesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Match> matches = new ArrayList<>();
    private IMatchDao matchDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchDao = DbHelper.getInstance(this.getContext()).getDao(IMatchDao.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last_matches, container, false);

        adapter = new MatchAdapter(getParentFragment(), matches);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        matches.clear();
        matches.addAll(matchDao.getAll());
        adapter.notifyDataSetChanged();

        return view;
    }
}