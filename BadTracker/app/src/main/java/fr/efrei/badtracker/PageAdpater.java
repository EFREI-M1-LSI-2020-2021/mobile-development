package fr.efrei.badtracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.fragments.LastMatchesFragment;
import fr.efrei.badtracker.fragments.MapMatchesFragment;

public class PageAdpater extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();

    PageAdpater(FragmentManager manager) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragments.add(new LastMatchesFragment());
        fragments.add(new MapMatchesFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
