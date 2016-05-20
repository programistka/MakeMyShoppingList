package net.programistka.shoppingadvisor.wizard;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.wizard.fragments.SevenDaysFragment;

public class WizardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment myFragment = new SevenDaysFragment();
        fragmentTransaction.add(R.id.myfragment, myFragment);
        fragmentTransaction.commit();
    }
}
