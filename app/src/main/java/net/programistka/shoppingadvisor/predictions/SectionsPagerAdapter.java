package net.programistka.shoppingadvisor.predictions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.programistka.shoppingadvisor.predictions.fragments.AllFragment;
import net.programistka.shoppingadvisor.predictions.fragments.SevenDaysFragment;
import net.programistka.shoppingadvisor.predictions.fragments.ThirtyDaysFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
        public Fragment getItem(int position) {
        //return PlaceholderFragment.newInstance(position + 1);
        switch (position) {
            case 0:
                return new AllFragment();
            case 1:
                return new SevenDaysFragment();
            case 2:
                return new ThirtyDaysFragment();
        }
        return new AllFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All";
            case 1:
                return "7 Days";
            case 2:
                return "30 days";
        }
        return null;
    }

    public void removeAll() {
    }
}
