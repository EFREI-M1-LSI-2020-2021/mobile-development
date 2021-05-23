package fr.efrei.badtracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.efrei.badtracker.R;
import fr.efrei.badtracker.view_holders.SetViewHolder;
import fr.efrei.badtracker.models.Set;

public class SetAdapter extends RecyclerView.Adapter<SetViewHolder>
{
    private final List<Set> sets;
    private final Fragment fragment;

    public SetAdapter(Fragment fragment, List<Set> sets) {
        this.sets = sets;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public SetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.set_item, parent, false);
        return new SetViewHolder(fragment, view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetViewHolder holder, int position) {
        Set set = sets.get(position);
        holder.show(set);
    }

    @Override
    public int getItemCount() {
        return sets.size();
    }
}