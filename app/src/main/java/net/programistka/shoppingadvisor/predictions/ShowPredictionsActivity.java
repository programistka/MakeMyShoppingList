package net.programistka.shoppingadvisor.predictions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemActivity;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.OnClick;

public class ShowPredictionsActivity extends AppCompatActivity {

    public static Menu menu;
    public static List<Long> selectedItems = new ArrayList<>();
    public static List<Long> copySelectedItems = new ArrayList<>();

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ShowPredictionsPresenter showPredictionsPresenter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_predictions);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    /*private void removeFragments(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.removeAll();
        viewPager.setAdapter(adapter);
    }*/

    public void markAsBought(MenuItem item) {
        initMenu();
        copySelectedItems.addAll(selectedItems);
        showPredictionsPresenter.markAsBought(selectedItems);
        mViewPager = (ViewPager) findViewById(R.id.container);
        final int currentFragment = mViewPager.getCurrentItem();
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentFragment);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if(selectedItems.size() > 1){
            alertDialogBuilder.setMessage("Items marked as bought.");
        }
        else {
            alertDialogBuilder.setMessage("Item marked as bought.");
        }
        alertDialogBuilder.setPositiveButton("Undo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                showPredictionsPresenter.undoMarkAsBought(copySelectedItems);
                mViewPager = (ViewPager) findViewById(R.id.container);
                final int currentFragment = mViewPager.getCurrentItem();
                SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
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
//        initMenu();
//        final ArchivePresenter presenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig(), this));
//        presenter.markAsArchived(selectedItems);
//        copySelectedItems.addAll(selectedItems);
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        int currentFragment = viewPager.getCurrentItem();
//        removeFragments(viewPager);
//        setupViewPager(viewPager, currentFragment);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        if(selectedItems.size() > 1) {
//            alertDialogBuilder.setMessage("Items archived."); }
//        else {
//            alertDialogBuilder.setMessage("Item archived.");
//        }
//        alertDialogBuilder.setPositiveButton("Undo", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                presenter.undoMarkAsArchived(copySelectedItems);
//                viewPager = (ViewPager) findViewById(R.id.viewpager);
//                int currentFragment = viewPager.getCurrentItem();
//                removeFragments(viewPager);
//                setupViewPager(viewPager, currentFragment);
//            }
//        });
//        showDialogForUndo(alertDialogBuilder);
//
    }

    public void markAsEmpty(MenuItem item) {
//        initMenu();
//        final ArchivePresenter presenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig(), this));
//        presenter.markAsEmpty(selectedItems, CalendarProvider.setNowCalendar().getTimeInMillis());
//        copySelectedItems.addAll(selectedItems);
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        int currentFragment = viewPager.getCurrentItem();
//        removeFragments(viewPager);
//        setupViewPager(viewPager, currentFragment);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        if(selectedItems.size() > 1) {
//            alertDialogBuilder.setMessage("Items marked as empty.");
//        }
//        else {
//            alertDialogBuilder.setMessage("Item marked as empty.");
//        }
//        alertDialogBuilder.setPositiveButton("Undo", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                presenter.undoMarkAsEmpty(copySelectedItems);
//                viewPager = (ViewPager) findViewById(R.id.viewpager);
//                int currentFragment = viewPager.getCurrentItem();
//                removeFragments(viewPager);
//                setupViewPager(viewPager, currentFragment);
//            }
//        });
//        showDialogForUndo(alertDialogBuilder);

    }

    private void showDialogForUndo(AlertDialog.Builder alertDialogBuilder) {
        final AlertDialog dialog = alertDialogBuilder.create();
        WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        wlmp.gravity = Gravity.BOTTOM;
        dialog.show();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                dialog.dismiss();
                timer.cancel();
            }
        }, 5000);
    }
//
//    private void initToolbar(){
//        setSupportActionBar(toolbar);
//    }
//
    @OnClick(R.id.plusButton)
    protected void attachFabAction() {
        startActivity(new Intent(ShowPredictionsActivity.this, AddEmptyItemActivity.class));
    }
}
