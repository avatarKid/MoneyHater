package vn.lol.moneyhater.moneyhater.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.adapter.FragmentPageAdapter;
import vn.lol.moneyhater.moneyhater.fragment.AccountFragment;
import vn.lol.moneyhater.moneyhater.fragment.BudgetFragment;
import vn.lol.moneyhater.moneyhater.fragment.ChartFragment;
import vn.lol.moneyhater.moneyhater.fragment.ListFragment;
import vn.lol.moneyhater.moneyhater.fragment.NavigationDrawerFragment;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private FragmentPageAdapter mPagerAdapter;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private Button mButtonAdd;
    private int StateSeleced = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        intialiseViewPager();
        mButtonAdd = (Button) findViewById(R.id.add_button);
        EventButtonAdd();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }


    private void EventButtonAdd(){
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (StateSeleced){
                    case 1:
                        //Account
                        Toast.makeText(getBaseContext(),"Account",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, NewBudgetActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //Budget
                        Toast.makeText(getBaseContext(),"Budget",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void intialiseViewPager() {
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, ListFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, AccountFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, BudgetFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ChartFragment.class.getName()));

        this.mPagerAdapter = new FragmentPageAdapter(super.getSupportFragmentManager(), fragments);
        //
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.addOnPageChangeListener(this);
    }

    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new FragmentPageAdapter(getSupportFragmentManager());
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        Log.i("KID", position + "");
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position);
        }

    }

    public void onSectionAttached(int number) {
        handleButtonState(number);
        StateSeleced = number-1;
    }

    private void handleButtonState(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                //Turn Off ButtonAdd
                mButtonAdd.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                //Turn On ButtonAdd
                mButtonAdd.setVisibility(View.VISIBLE);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                //Turn On ButtonAdd
                mButtonAdd.setVisibility(View.VISIBLE);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                //Turn Off ButtonAdd
                mButtonAdd.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        handleButtonState(position);
        StateSeleced = position -1;
        restoreActionBar();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
