package net.programistka.shoppingadvisor.predictions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemActivity;
import net.programistka.shoppingadvisor.presenters.ArchivePresenter;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowPredictionsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    public static Menu menu;
    public static List<Long> selectedItems = new ArrayList<>();

    private ShowPredictionsPresenter presenter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_predictions);
        ButterKnife.bind(this);

        initToolbar();

        presenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig(), this));
        PredictionsAdapter adapter = new PredictionsAdapter(presenter.getPredictions());

        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        if(recyclerView == null) {
            return;
        }
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_predictions, menu);
        ShowPredictionsActivity.menu = menu;
        return true;
    }

    public void markAsBought(MenuItem item) {
        presenter.markAsBought(selectedItems);
        recyclerView.invalidate();
    }

    public void markAsArchived(MenuItem item) {
        ArchivePresenter presenter = new ArchivePresenter(new DbConfig(), this);
        presenter.markAsArchived(selectedItems);
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.plusButton)
    protected void attachFabAction() {
        startActivity(new Intent(ShowPredictionsActivity.this, AddEmptyItemActivity.class));
    }
}
