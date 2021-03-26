package fr.android.calculator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.android.calculator.fragments.DefaultFragment;
import fr.android.calculator.fragments.GridFragment;

public class PageAdpater extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();

    PageAdpater(FragmentManager manager) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragments.add(new DefaultFragment());
        fragments.add(new GridFragment());
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
