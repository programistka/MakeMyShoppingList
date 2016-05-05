package net.programistka.shoppingadvisor.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.acitivities.ActivityWithFab;
import net.programistka.shoppingadvisor.predictions.ShowPredictionsActivity;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ActivityWithFab implements MainActivityView {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        attachFabAction();
    }

    @Override
    @OnClick(R.id.showAddNewEmptyItem)
    public void navigateToAddNewEmptyItem(){
        startActivity(new Intent(this, AddEmptyItemActivity.class));
    }

    @Override
    @OnClick(R.id.showPredictions)
    public void navigateToShowPredictions(){
        startActivity(new Intent(this, ShowPredictionsActivity.class));
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
    }
}
