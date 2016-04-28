package net.programistka.shoppingadvisor.acitivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import net.programistka.shoppingadvisor.R;

public class MainActivity extends ActivityWithFab {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void loadShowEmptyItemsHistoryView(View view){
        startActivity(new Intent(this, ShowEmptyItemsHistoryActivity.class));
    }

    public void loadAddEmptyItemView(View view){
        startActivity(new Intent(this, AddEmptyItemActivity.class));
    }

    public void loadShowPredictionsView(View view){
        startActivity(new Intent(this, ShowPredictionsActivity.class));
    }
}
