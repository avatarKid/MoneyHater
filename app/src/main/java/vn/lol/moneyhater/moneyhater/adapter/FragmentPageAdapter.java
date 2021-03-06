package vn.lol.moneyhater.moneyhater.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FragmentPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    public FragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        ((ViewPager) container).removeView((View) object);
    }
}
