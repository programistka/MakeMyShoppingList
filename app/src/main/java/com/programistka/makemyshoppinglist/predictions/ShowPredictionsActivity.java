package com.programistka.makemyshoppinglist.predictions;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.programistka.makemyshoppinglist.CalendarProvider;
import com.programistka.makemyshoppinglist.HelpActivity;
import com.programistka.makemyshoppinglist.LoginActivity;
import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.addemptyitem.AddEmptyItemActivity;
import com.programistka.makemyshoppinglist.archive.ArchiveInteractor;
import com.programistka.makemyshoppinglist.archive.ArchivePresenter;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowPredictionsActivity extends AppCompatActivity {

    public static Menu menu;
    public static List<Long> selectedItems = new ArrayList<>();
    public static List<Long> copySelectedItems = new ArrayList<>();

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ShowPredictionsPresenter showPredictionsPresenter;

    private ViewPager mViewPager;

    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_predictions);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig(), this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_predictions, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            startActivity(new Intent(ShowPredictionsActivity.this, HelpActivity.class));
            return true;
        }
        if (id == R.id.login) {
            startActivity(new Intent(ShowPredictionsActivity.this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void markAsBought(MenuItem item) {
        initMenu();
        copySelectedItems = new ArrayList<>();
        copySelectedItems.addAll(selectedItems);
        showPredictionsPresenter.markAsBought(selectedItems);
        mViewPager = (ViewPager) findViewById(R.id.container);
        final int currentFragment = mViewPager.getCurrentItem();
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentFragment);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if(selectedItems.size() > 1){
            alertDialogBuilder.setMessage(this.getString(R.string.itemsMarkedAsBought));
        }
        else {
            alertDialogBuilder.setMessage(this.getString(R.string.itemMarkedAsBought));
        }
        alertDialogBuilder.setPositiveButton(this.getString(R.string.undo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                showPredictionsPresenter.undoMarkAsBought(copySelectedItems);
                mViewPager = (ViewPager) findViewById(R.id.container);
                final int currentFragment = mViewPager.getCurrentItem();
                Dialog dialog = (Dialog)arg0;
                SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), dialog.getContext());
                mViewPager.setAdapter(adapter);
                mViewPager.setCurrentItem(currentFragment);
            }
        });
        showDialogForUndo(alertDialogBuilder);
    }

    private void initMenu() {
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);
        menu.getItem(3).setVisible(false);
    }

    public void markAsArchived(MenuItem item) {
        initMenu();
        final ArchivePresenter presenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig(), this));
        presenter.markAsArchived(selectedItems);
        copySelectedItems.addAll(selectedItems);
        mViewPager = (ViewPager) findViewById(R.id.container);
        int currentFragment = mViewPager.getCurrentItem();
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentFragment);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);//, R.style.CustomDialogTheme);
        if(selectedItems.size() > 1) {
            alertDialogBuilder.setMessage(this.getString(R.string.itemsArchived));
        } else {
            alertDialogBuilder.setMessage(this.getString(R.string.itemArchived));
        }
        alertDialogBuilder.setPositiveButton(this.getString(R.string.undo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                presenter.undoMarkAsArchived(copySelectedItems);
                mViewPager = (ViewPager) findViewById(R.id.container);
                int currentFragment = mViewPager.getCurrentItem();
                Dialog dialog = (Dialog)arg0;
                SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), dialog.getContext());
                mViewPager.setAdapter(adapter);
                mViewPager.setCurrentItem(currentFragment);
            }
        });
        showDialogForUndo(alertDialogBuilder);

    }

    public void markAsEmpty(MenuItem item) {
        initMenu();
        final ArchivePresenter presenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig(), this));
        presenter.markAsEmpty(selectedItems, CalendarProvider.setNowCalendar().getTimeInMillis());
        copySelectedItems.addAll(selectedItems);
        mViewPager = (ViewPager) findViewById(R.id.container);
        int currentFragment = mViewPager.getCurrentItem();
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentFragment);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if(selectedItems.size() > 1) {
            alertDialogBuilder.setMessage(getString(R.string.itemsMarkedAsEmpty));
        }
        else {
            alertDialogBuilder.setMessage(getString(R.string.itemMarkedAsEmpty));
        }
        alertDialogBuilder.setPositiveButton(this.getString(R.string.undo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                presenter.undoMarkAsEmpty(copySelectedItems);
                mViewPager = (ViewPager) findViewById(R.id.container);
                int currentFragment = mViewPager.getCurrentItem();
                Dialog dialog = (Dialog)arg0;
                SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), dialog.getContext());
                mViewPager.setAdapter(adapter);
                mViewPager.setCurrentItem(currentFragment);
            }
        });
        showDialogForUndo(alertDialogBuilder);
    }

    private void showDialogForUndo(AlertDialog.Builder alertDialogBuilder) {
        final AlertDialog dialog = alertDialogBuilder.create();
        WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER;
        wlmp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(wlmp);

        dialog.show();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                dialog.dismiss();
                timer.cancel();
            }
        }, 5000);
    }

    @Override
    public void onBackPressed() {
        if (this.exit) {
            ActivityCompat.finishAffinity(this);
        } else {
            Toast.makeText(this, this.getString(R.string.pressBackAgainToExit), Toast.LENGTH_SHORT).show();
            this.exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ShowPredictionsActivity.this.exit = false;
                }
            }, 3 * 1000);
        }
    }

    @OnClick(R.id.plusButton)
    protected void attachFabAction() {
        startActivity(new Intent(ShowPredictionsActivity.this, AddEmptyItemActivity.class));
    }
}
