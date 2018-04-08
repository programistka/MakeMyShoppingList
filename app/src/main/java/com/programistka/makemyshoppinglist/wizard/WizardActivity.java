package com.programistka.makemyshoppinglist.wizard;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.wizard.fragments.SevenDaysFragment;
import com.programistka.makemyshoppinglist.wizard.fragments.StartFragment;
import com.programistka.makemyshoppinglist.wizard.fragments.ThirtyDaysFragment;

import butterknife.ButterKnife;

public class WizardActivity extends Activity {

    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setContentView(R.layout.activity_wizard);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment myFragment = new StartFragment();
        fragmentTransaction.replace(R.id.myfragment, myFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment current = fragmentManager.findFragmentById(R.id.myfragment);
        if(current instanceof ThirtyDaysFragment || current instanceof SevenDaysFragment) {
            super.onBackPressed();
        }
        else if(current instanceof StartFragment) {
            if (this.exit) {
                ActivityCompat.finishAffinity(this);
            } else {
                Toast.makeText(this, this.getString(R.string.pressBackAgainToExit), Toast.LENGTH_SHORT).show();
                this.exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WizardActivity.this.exit = false;
                    }
                }, 3 * 1000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
