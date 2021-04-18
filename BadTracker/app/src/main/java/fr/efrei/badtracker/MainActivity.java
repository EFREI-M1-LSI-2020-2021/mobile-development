package fr.efrei.badtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.interfaces.IMatchDao;
import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.MatchLocation;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Set;
import fr.efrei.badtracker.models.Sex;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new PageAdpater(getSupportFragmentManager()));
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

        tabLayout = findViewById(R.id.tabLayout);
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

        DbHelper dbHelper = DbHelper.getInstance(this);

        List<Player> winners = new ArrayList<Player>() {{
            add(new Player("REMEUR", "JM", Sex.Male, "FR"));
        }};

        List<Player> losers = new ArrayList<Player>() {{
            add(new Player("LACAZE", "Thomas", Sex.Male, "FR"));
        }};

        IMatchDao matchDao = dbHelper.getDao(IMatchDao.class);
        matchDao.add(new Match(
                "Test",
                new MatchLocation(12, 12, "Street"),
                winners,
                losers,
                new ArrayList<Set>() {{
                    add(new Set(21, 2, winners, losers));
                    add(new Set(21, 1, winners, losers));
                }}
        ));

        List<Match> matches = matchDao.getAll();
        System.out.println(matches);
    }
}