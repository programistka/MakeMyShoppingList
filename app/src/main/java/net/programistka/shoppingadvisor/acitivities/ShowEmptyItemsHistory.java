package net.programistka.shoppingadvisor.acitivities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import net.programistka.shoppingadvisor.dbhandlers.DbHandler;
import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.adapters.EmptyItemsHistory;

public class ShowEmptyItemsHistory extends ActivityWithFab {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createActivity(this);
    }

    private static void createActivity(ShowEmptyItemsHistory showEmptyItemsHistory) {
        showEmptyItemsHistory.setContentView(R.layout.activity_show_history);

        Toolbar toolbar = (Toolbar) showEmptyItemsHistory.findViewById(R.id.toolbar);
        showEmptyItemsHistory.setSupportActionBar(toolbar);

        DbHandler dbHandler = new DbHandler(showEmptyItemsHistory);
        EmptyItemsHistory adapter = new EmptyItemsHistory(dbHandler.selectAllItemsFromEmptyItemsHistoryTable());

        RecyclerView recyclerView = (RecyclerView) showEmptyItemsHistory.findViewById(R.id.lvItems);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(showEmptyItemsHistory);
        recyclerView.setLayoutManager(layoutManager);

        showEmptyItemsHistory.attachFabAction();
    }
}