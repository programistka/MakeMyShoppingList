package com.programistka.makemyshoppinglist.predictions;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.predictions.fragments.AllFragment;
import com.programistka.makemyshoppinglist.predictions.fragments.AllFragmentNew;
import com.programistka.makemyshoppinglist.predictions.fragments.SevenDaysFragment;
import com.programistka.makemyshoppinglist.predictions.fragments.ThirtyDaysFragment;

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
                return new AllFragmentNew();
            case 1:
                return new AllFragment();
            case 2:
                return new SevenDaysFragment();
            case 3:
                return new ThirtyDaysFragment();
        }
        return new AllFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All-new";
            case 1:
                return context.getString(R.string.all);
            case 2:
                return context.getString(R.string.thisWeek);
            case 3:
                return context.getString(R.string.thisMonth);
        }
        return null;
    }
}
