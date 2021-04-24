package fr.efrei.badtracker.fragments.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import fr.efrei.badtracker.services.ApiService;
import fr.efrei.badtracker.R;
import fr.efrei.badtracker.api.MatchApi;
import fr.efrei.badtracker.api.dtos.MatchDto;
import fr.efrei.badtracker.fragments.main.adapters.PageAdapter;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fab;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new PageAdapter(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*DbHelper dbHelper = DbHelper.getInstance(getContext());

        IMatchDao matchDao = dbHelper.getDao(IMatchDao.class);
        matchDao.add(new Match(
                "Test",
                new MatchLocation(12, 12),
                new ArrayList<Player>() {{
                    add(new Player("REMEUR", "JM", Sex.Male, "FR"));
                }},
                new ArrayList<Player>() {{
                    add(new Player("LACAZE", "Thomas", Sex.Male, "FR"));
                }},
                new ArrayList<Set>() {{
                    add(new Set(21, 2));
                    add(new Set(21, 1));
                }}
        ));

        List<Match> matches = matchDao.getAll();
        System.out.println(matches);*/

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_createMatchFragment));

        return view;
    }
}