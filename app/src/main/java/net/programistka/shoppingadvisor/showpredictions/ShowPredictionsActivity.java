package net.programistka.shoppingadvisor.showpredictions;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.acitivities.ActivityWithFab;
import net.programistka.shoppingadvisor.adapters.PredictionsAdapter;
import net.programistka.shoppingadvisor.presenters.ArchivePresenter;
import net.programistka.shoppingadvisor.presenters.PredictionsPresenter;

import java.util.ArrayList;
import java.util.List;

public class ShowPredictionsActivity extends ActivityWithFab {

    public static Menu menu;
    public static List<Long> selectedItems = new ArrayList<>();

    private PredictionsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createActivity();
    }

    private void createActivity() {
        setContentView(R.layout.activity_show_predictions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new PredictionsPresenter(this);
        PredictionsAdapter adapter = new PredictionsAdapter(presenter.getPredictions());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        attachFabAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_predictions, menu);
        ShowPredictionsActivity.menu = menu;
        return true;
    }

    public void markAsBought(MenuItem item) {
        presenter.markAsBought(selectedItems);
    }

    public void markAsArchived(MenuItem item) {
        ArchivePresenter presenter = new ArchivePresenter(this);
        presenter.markAsArchived(selectedItems);
    }
}
