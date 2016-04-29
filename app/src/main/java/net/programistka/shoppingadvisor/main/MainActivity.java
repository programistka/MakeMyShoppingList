package net.programistka.shoppingadvisor.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.acitivities.ActivityWithFab;
import net.programistka.shoppingadvisor.acitivities.ShowEmptyItemsHistoryActivity;
import net.programistka.shoppingadvisor.showpredictions.ShowPredictionsActivity;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ActivityWithFab implements MainActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        attachFabAction();
    }

    @Override
    @OnClick(R.id.showAddNewEmptyItem)
    public void navigateToAddNewEmptyItem() {
        startActivity(new Intent(this, AddEmptyItemActivity.class));
    }

    @Override
    @OnClick(R.id.showHistory)
    public void navigateToShowHistory() {
        startActivity(new Intent(this, ShowEmptyItemsHistoryActivity.class));
    }

    @Override
    @OnClick(R.id.showPredictions)
    public void navigateToShowPredictions(){
        startActivity(new Intent(this, ShowPredictionsActivity.class));
    }
}
