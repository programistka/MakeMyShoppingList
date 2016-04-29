package net.programistka.shoppingadvisor.acitivities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.adapters.PredictionsAdapter;
import net.programistka.shoppingadvisor.dbhandlers.ArchiveDbHandler;
import net.programistka.shoppingadvisor.dbhandlers.PredictionsDbHandler;

import java.util.ArrayList;
import java.util.List;

public class ShowPredictions extends ActivityWithFab {

    public static Menu menu;
    public static List<Long> selectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createActivity();
    }

    private void createActivity() {
        setContentView(R.layout.activity_show_predictions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PredictionsDbHandler dbHandler = new PredictionsDbHandler(this);
        PredictionsAdapter adapter = new PredictionsAdapter(dbHandler.getPredictions());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        attachFabAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_predictions, menu);
        ShowPredictions.menu = menu;
        return true;
    }

    public void markAsBought(MenuItem item) {
        PredictionsDbHandler dbHandler = new PredictionsDbHandler(this);
        for (Long itemId:selectedItems) {
            dbHandler.insertBoughtPredictionIntoPredictionsTable(itemId);
        }
    }

    public void markAsArchived(MenuItem item) {
        ArchiveDbHandler dbHandler = new ArchiveDbHandler(this);
        for (Long itemId:selectedItems) {
            dbHandler.insertItemToArchiveTable(itemId);
        }
    }
}
