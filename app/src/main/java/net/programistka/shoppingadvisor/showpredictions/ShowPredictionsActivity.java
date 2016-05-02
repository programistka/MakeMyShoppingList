package net.programistka.shoppingadvisor.showpredictions;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.acitivities.ActivityWithFab;
import net.programistka.shoppingadvisor.presenters.ArchivePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowPredictionsActivity extends ActivityWithFab {

    @BindView(R.id.toolbar) Toolbar toolbar;

    public static Menu menu;
    public static List<Long> selectedItems = new ArrayList<>();

    private ShowPredictionsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_predictions);
        ButterKnife.bind(this);

        initToolbar();

        presenter = new ShowPredictionsPresenter(this);
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

    private void initToolbar(){
        setSupportActionBar(toolbar);
    }
}
