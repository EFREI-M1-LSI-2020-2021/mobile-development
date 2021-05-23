package fr.efrei.badtracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.view_holders.PlayerViewHolder;
import fr.efrei.badtracker.models.Player;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder>
{
    private final List<Player> players;
    private final Fragment fragment;

    public PlayerAdapter(Fragment fragment, List<Player> players) {
        this.players = players;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(fragment, view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.show(player);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}