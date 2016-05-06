package net.programistka.shoppingadvisor.predictions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemActivity;
import net.programistka.shoppingadvisor.archive.ArchiveInteractor;
import net.programistka.shoppingadvisor.archive.ArchivePresenter;
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Items Bought.");
        alertDialogBuilder.setPositiveButton("Undo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                presenter.undoMarkAsBought(selectedItems);
            }
        });
        AlertDialog dialog = alertDialogBuilder.create();
        WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        wlmp.gravity = Gravity.BOTTOM;
        dialog.show();
    }

    public void markAsArchived(MenuItem item) {
        final ArchivePresenter presenter = new ArchivePresenter(new ArchiveInteractor(new DbConfig(), this));
        presenter.markAsArchived(selectedItems);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Items archived.");
        alertDialogBuilder.setPositiveButton("Undo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                presenter.undoMarkAsArchived(selectedItems);
            }
        });
        AlertDialog dialog = alertDialogBuilder.create();
        WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        wlmp.gravity = Gravity.BOTTOM;
        dialog.show();
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.plusButton)
    protected void attachFabAction() {
        startActivity(new Intent(ShowPredictionsActivity.this, AddEmptyItemActivity.class));
    }
}
