package net.programistka.shoppingadvisor.acitivities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.adapters.EmptyItemsHistoryAdapter;
import net.programistka.shoppingadvisor.presenters.DbConfig;
import net.programistka.shoppingadvisor.selectallItems.SelectAllItemsInteractor;
import net.programistka.shoppingadvisor.selectallItems.SelectAllItemsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowEmptyItemsHistoryActivity extends ActivityWithFab {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        ButterKnife.bind(this);

        initToolbar();

        SelectAllItemsPresenter presenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig(), getApplicationContext()));
        EmptyItemsHistoryAdapter adapter = new EmptyItemsHistoryAdapter(presenter.selectAllItemsFromEmptyItemsHistoryTable());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        if(recyclerView == null){
            return;
        }
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        this.attachFabAction();
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
    }
}