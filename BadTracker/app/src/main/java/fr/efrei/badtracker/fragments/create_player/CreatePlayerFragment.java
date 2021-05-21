package fr.efrei.badtracker.fragments.create_player;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Sex;

public class CreatePlayerFragment extends Fragment {

    private EditText nameEditText;
    private EditText firstNameEditText;
    private RadioGroup sexRadioGroup;
    private EditText nationalityEditText;
    private Button createButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_player, container, false);
        setRetainInstance(true);

        nameEditText = view.findViewById(R.id.name);
        firstNameEditText = view.findViewById(R.id.firstname);
        sexRadioGroup = view.findViewById(R.id.sex);
        nationalityEditText = view.findViewById(R.id.nationality);
        createButton = view.findViewById(R.id.create);
        createButton.setOnClickListener(this::create);

        return view;
    }

    private Sex getSelectedSex() {
        int id = sexRadioGroup.getCheckedRadioButtonId();
        if(id == R.id.female) {
            return Sex.Female;
        }
        else if(id == R.id.male) {
            return Sex.Male;
        }
        else {
            return Sex.Other;
        }
    }

    private void create(View view) {
        String name = nameEditText.getText().toString();
        if(name.isEmpty()) {
            return;
        }

        String firstName = firstNameEditText.getText().toString();
        if(firstName.isEmpty()) {
            return;
        }

        Sex sex = getSelectedSex();

        String nationality = nationalityEditText.getText().toString();
        if(nationality.isEmpty()) {
            return;
        }

        Player player = new Player(name, firstName, sex, nationality);

        NavController navController = NavHostFragment.findNavController(this);
        navController.getPreviousBackStackEntry().getSavedStateHandle().set("player", player);
        navController.popBackStack();
    }
}