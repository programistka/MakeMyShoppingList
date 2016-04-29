package net.programistka.shoppingadvisor.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.acitivities.ActivityWithFab;
import net.programistka.shoppingadvisor.acitivities.AddEmptyItemActivity;
import net.programistka.shoppingadvisor.acitivities.ShowEmptyItemsHistoryActivity;
import net.programistka.shoppingadvisor.acitivities.ShowPredictionsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ActivityWithFab implements MainActivityView {

    @BindView(R.id.addShoppingItem) Button addShoppingItem;
    @BindView(R.id.showHistory) Button showHistory;
    @BindView(R.id.showPredictions) Button showPredictions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        attachFabAction();
    }

    @OnClick(R.id.addShoppingItem)
    public void navigateToAddNewEmptyItem() {
        startActivity(new Intent(this, AddEmptyItemActivity.class));
    }

    @OnClick(R.id.showHistory)
    public void navigateToShowHistory() {
        startActivity(new Intent(this, ShowEmptyItemsHistoryActivity.class));
    }

    @OnClick(R.id.showPredictions)
    public void navigateToShowPredictions(){
        startActivity(new Intent(this, ShowPredictionsActivity.class));
    }
}
