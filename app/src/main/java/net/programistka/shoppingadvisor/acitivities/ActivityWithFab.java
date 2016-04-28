package net.programistka.shoppingadvisor.acitivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.programistka.shoppingadvisor.R;

public class ActivityWithFab extends AppCompatActivity {
    protected void attachFabAction() {
        View addButton = findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityWithFab.this, AddEmptyItem.class);
                startActivity(intent);
            }
        });
    }
}
