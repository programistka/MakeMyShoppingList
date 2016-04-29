package net.programistka.shoppingadvisor.acitivities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.adapters.EmptyItemsHistory;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemInteractor;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemPresenter;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemView;
import net.programistka.shoppingadvisor.selectallItems.SelectAllItemsInteractor;
import net.programistka.shoppingadvisor.selectallItems.SelectAllItemsPresenter;

public class ShowEmptyItemsHistoryActivity extends ActivityWithFab {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SelectAllItemsInteractor interactor = new SelectAllItemsInteractor(getApplicationContext());
        SelectAllItemsPresenter presenter = new SelectAllItemsPresenter(interactor);
        EmptyItemsHistory adapter = new EmptyItemsHistory(presenter.selectAllItemsFromEmptyItemsHistoryTable());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        this.attachFabAction();
    }
}