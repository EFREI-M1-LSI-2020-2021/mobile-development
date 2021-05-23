package fr.efrei.badtracker.fragments.create_set;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.models.Set;

public class CreateSetFragment extends Fragment {

    private EditText scoreTeam1EditText;
    private EditText scoreTeam2EditText;
    private Button createButton;
    private Bundle args;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_set, container, false);
        setRetainInstance(true);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());

        args = getArguments();

        scoreTeam1EditText = view.findViewById(R.id.score1);
        scoreTeam2EditText = view.findViewById(R.id.score2);
        createButton = view.findViewById(R.id.create);
        createButton.setOnClickListener(this::create);

        return view;
    }

    private void create(View view) {
        String score1Text = scoreTeam1EditText.getText().toString();
        if(score1Text.isEmpty()) {
            return;
        }

        String score2Text = scoreTeam2EditText.getText().toString();
        if(score2Text.isEmpty()) {
            return;
        }

        int score1, score2;

        try {
            score1 = Integer.parseInt(score1Text);
            score2 = Integer.parseInt(score2Text);
        }
        catch (Exception e) {
            return;
        }

        Set set = new Set(score1, score2);

        NavController navController = NavHostFragment.findNavController(this);
        navController.getPreviousBackStackEntry().getSavedStateHandle().set("set", set);
        navController.getPreviousBackStackEntry().getSavedStateHandle().set("index", args.getInt("index"));
        navController.getPreviousBackStackEntry().getSavedStateHandle().set("match", args.getSerializable("match"));
        navController.popBackStack();
    }
}