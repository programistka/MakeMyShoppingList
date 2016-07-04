package net.programistka.shoppingadvisor.predictions;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.predictions.fragments.AllFragment;
import net.programistka.shoppingadvisor.predictions.fragments.SevenDaysFragment;
import net.programistka.shoppingadvisor.predictions.fragments.ThirtyDaysFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        context = ctx;
    }

    @Override
        public Fragment getItem(int position) {
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
                return context.getString(R.string.all);
            case 1:
                return context.getString(R.string.thisWeek);
            case 2:
                return context.getString(R.string.thisMonth);
        }
        return null;
    }

    public void removeAll() {
    }
}
