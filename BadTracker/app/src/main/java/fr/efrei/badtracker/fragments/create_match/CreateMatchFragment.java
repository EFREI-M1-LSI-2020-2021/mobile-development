package fr.efrei.badtracker.fragments.create_match;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import fr.efrei.badtracker.R;

public class CreateMatchFragment extends Fragment {

    private String matchType;
    private ProgressBar progressBar;

    public CreateMatchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchType = CreateMatchFragmentArgs.fromBundle(getArguments()).getMatchType();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_match, container, false);

        progressBar = view.findViewById(R.id.progressBar);

        return view;
    }
}