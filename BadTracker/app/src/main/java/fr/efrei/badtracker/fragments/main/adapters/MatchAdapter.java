package fr.efrei.badtracker.fragments.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.fragments.main.viewHolder.MatchViewHolder;
import fr.efrei.badtracker.models.Match;

public class MatchAdapter extends RecyclerView.Adapter<MatchViewHolder>
{
    private List<Match> matches = null;

    public MatchAdapter(List<Match> matches)
    {
        if(matches != null)
        {
            this.matches = matches;
        }
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.match_item, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position)
    {
        Match match = matches.get(position);
        holder.show(match);
    }

    public int getItemCount()
    {
        if(matches != null)
            return matches.size();
        return 0;
    }
}