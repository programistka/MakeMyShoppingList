package net.programistka.shoppingadvisor.predictions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.programistka.shoppingadvisor.predictions.fragments.AllFragment;
import net.programistka.shoppingadvisor.predictions.fragments.SevenDaysFragment;
import net.programistka.shoppingadvisor.predictions.fragments.ThirtyDaysFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    int numOfTabs;

    public ViewPagerAdapter(FragmentManager manager, int numOfTabs) {
        super(manager);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AllFragment tab1 = new AllFragment();
                return tab1;
            case 1:
                SevenDaysFragment tab2 = new SevenDaysFragment();
                return tab2;
            case 2:
                ThirtyDaysFragment tab3 = new ThirtyDaysFragment();
                return tab3;
            default:
                return null;
        }    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void removeAll() {
        mFragmentList.clear();
        mFragmentTitleList.clear();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
