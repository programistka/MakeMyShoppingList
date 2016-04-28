package net.programistka.shoppingadvisor.acitivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.programistka.shoppingadvisor.DbHandler;
import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.adapters.HistoryAdapter;

public class ShowHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createActivity(this);
    }

    private static void createActivity(ShowHistory showHistory) {
        showHistory.setContentView(R.layout.activity_show_history);
        Toolbar toolbar = (Toolbar) showHistory.findViewById(R.id.toolbar);
        showHistory.setSupportActionBar(toolbar);

        DbHandler dbHandler = new DbHandler(showHistory);
        HistoryAdapter adapter = new HistoryAdapter(dbHandler.selectAllItemsFromHistoryTable());

        RecyclerView recyclerView = (RecyclerView) showHistory.findViewById(R.id.lvItems);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(showHistory);
        recyclerView.setLayoutManager(layoutManager);

        showHistory.attachFabAction();
    }

    private void attachFabAction() {
        View addButton = findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowHistory.this, AddEmptyItemEvent.class);
                startActivity(intent);
            }
        });
    }
}