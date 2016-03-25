package net.programistka.shoppingadvisor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class ShowPredictions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_predictions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DbHandler dbHandler = new DbHandler(this);
        PredictionsAdapter adapter = new PredictionsAdapter(this, dbHandler.getPredictions());

        ListView itemsListView = (ListView) findViewById(R.id.lvItems);
        itemsListView.setAdapter(adapter);
    }

}
