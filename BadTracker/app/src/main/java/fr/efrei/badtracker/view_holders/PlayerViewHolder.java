package fr.efrei.badtracker.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.models.Player;

public class PlayerViewHolder extends RecyclerView.ViewHolder
{
    private final TextView name;
    private final TextView firstname;
    private final TextView sex;
    private final TextView nationality;
    private Player player;

    public PlayerViewHolder(Fragment fragment, final View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        firstname = itemView.findViewById(R.id.firstname);
        sex = itemView.findViewById(R.id.sex);
        nationality = itemView.findViewById(R.id.nationality);
    }

    public void show(Player player) {
        this.player = player;

        name.setText(player.getName());
        firstname.setText(player.getFirstName());
        sex.setText(player.getSex().name());
        nationality.setText(player.getNationality());
    }
}