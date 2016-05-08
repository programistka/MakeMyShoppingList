package net.programistka.shoppingadvisor.predictions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemActivity;
import net.programistka.shoppingadvisor.archive.ArchiveInteractor;
import net.programistka.shoppingadvisor.archive.ArchivePresenter;
import net.programistka.shoppingadvisor.predictions.fragments.AllFragment;
import net.programistka.shoppingadvisor.predictions.fragments.SevenDaysFragment;
import net.programistka.shoppingadvisor.predictions.fragments.ThirtyDaysFragment;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowPredictionsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static Menu menu;
    public static List<Long> selectedItems = new ArrayList<>();
    public static List<Long> copySelectedItems = new ArrayList<>();

    private ShowPredictionsPresenter showPredictionsPresenter;
    PredictionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_predictions);
        ButterKnife.bind(this);

        initToolbar();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        showPredictionsPresenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig(), this));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllFragment(), "ALL");
        adapter.addFragment(new SevenDaysFragment(), "7 DAYS");
        adapter.addFragment(new ThirtyDaysFragment(), "30 DAYS");
        viewPager.setAdapter(adapter);
    }

    private void removeFragments(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.removeAll();
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_predictions, menu);
        ShowPredictionsActivity.menu = menu;
        return true;
    }

    public void markAsBought(MenuItem item) {
        copySelectedItems.addAll(selectedItems);
        showPredictionsPresenter.markAsBought(selectedItems);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        removeFragments(viewPager);
        setupViewPager(viewPager);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Items Bought.");
        alertDialogBuilder.setPositiveButton("Undo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                showPredictionsPresenter.undoMarkAsBought(copySelectedItems);
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                removeFragments(viewPager);
                setupViewPager(viewPager);
            }
        });
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
        }, 2000);

    }

    public void markAsArchived(MenuItem item) {
        final ArchivePresenter presenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig(), this));
        presenter.markAsArchived(selectedItems);
        copySelectedItems.addAll(selectedItems);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        removeFragments(viewPager);
        setupViewPager(viewPager);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Items archived.");
        alertDialogBuilder.setPositiveButton("Undo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                presenter.undoMarkAsArchived(copySelectedItems);
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                removeFragments(viewPager);
                setupViewPager(viewPager);
            }
        });
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

    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.plusButton)
    protected void attachFabAction() {
        startActivity(new Intent(ShowPredictionsActivity.this, AddEmptyItemActivity.class));
    }
}
