package net.programistka.shoppingadvisor.acitivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import net.programistka.shoppingadvisor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ActivityWithFab {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.addShoppingItem)
    public void loadAddEmptyItemView(View view){
        startActivity(new Intent(this, AddEmptyItemActivity.class));
    }

    @OnClick(R.id.showHistory)
    public void loadShowEmptyItemsHistoryView(View view){
        startActivity(new Intent(this, ShowEmptyItemsHistoryActivity.class));
    }

    @OnClick(R.id.showPredictions)
    public void loadShowPredictionsView(View view){
        startActivity(new Intent(this, ShowPredictionsActivity.class));
    }
}
