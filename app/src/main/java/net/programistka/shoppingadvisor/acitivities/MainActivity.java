package net.programistka.shoppingadvisor.acitivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import net.programistka.shoppingadvisor.R;

public class MainActivity extends AppCompatActivity {

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

    public void loadShowHistory(View view){
        Intent intent = new Intent(this, ShowHistory.class);
        startActivity(intent);
    }

    public void loadAddEmptyItemEvent(View view){
        Intent intent = new Intent(this, AddEmptyItemEvent.class);
        startActivity(intent);
    }

    public void loadShowPredictions(View view){
        Intent intent = new Intent(this, ShowPredictions.class);
        startActivity(intent);
    }

    private void attachFabAction() {
        View addButton = findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEmptyItemEvent.class);
                startActivity(intent);
            }
        });
    }
}
