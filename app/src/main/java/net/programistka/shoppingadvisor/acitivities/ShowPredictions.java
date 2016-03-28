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
import net.programistka.shoppingadvisor.adapters.PredictionsAdapter;

public class ShowPredictions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_predictions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DbHandler dbHandler = new DbHandler(this);
        PredictionsAdapter adapter = new PredictionsAdapter(dbHandler.getPredictions());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        attachFabAction();
    }

    private void attachFabAction() {
        View addButton = findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPredictions.this, AddShoppingEvent.class);
                startActivity(intent);
            }
        });
    }
}
