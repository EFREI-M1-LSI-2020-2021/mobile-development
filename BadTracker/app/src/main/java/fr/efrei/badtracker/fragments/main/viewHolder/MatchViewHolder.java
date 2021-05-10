package fr.efrei.badtracker.fragments.main.viewHolder;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Locale;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.models.Match;

public class MatchViewHolder extends RecyclerView.ViewHolder
{
    private final TextView name;
    private final TextView date;
    private final TextView teams;
    private final DateFormat df;
    private Match match;

    public MatchViewHolder(final View itemView)
    {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        teams = itemView.findViewById(R.id.teams);
        date = itemView.findViewById(R.id.date);

        df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());

        itemView.setOnClickListener(view -> {

            // TODO: Get address name from long lat
            new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Match")
                    .setMessage(match.getName())
                    .show();
        });
    }

    public void show(Match match)
    {
        this.match = match;

        name.setText(match.getName());
        date.setText(df.format(match.getDate()));
        teams.setText(match.getTeamName(match.getTeam1()) +"\n - \n"+ match.getTeamName(match.getTeam2()));
    }


}