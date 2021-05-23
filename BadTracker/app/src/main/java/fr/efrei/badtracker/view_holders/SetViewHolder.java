package fr.efrei.badtracker.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.models.Set;

public class SetViewHolder extends RecyclerView.ViewHolder
{
    private final TextView score1;
    private final TextView score2;
    private Set set;

    public SetViewHolder(Fragment fragment, final View itemView) {
        super(itemView);

        score1 = itemView.findViewById(R.id.score1);
        score2 = itemView.findViewById(R.id.score2);
    }

    public void show(Set set) {
        this.set = set;

        score1.setText("Score team 1 : " + set.getScoreTeam1());
        score2.setText("Score team 2 : " + set.getScoreTeam2());
    }
}