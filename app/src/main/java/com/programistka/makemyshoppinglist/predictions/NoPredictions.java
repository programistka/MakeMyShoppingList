package com.programistka.makemyshoppinglist.predictions;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.addemptyitem.AddEmptyItemActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoPredictions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_predictions);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.plusButton)
    protected void attachFabAction() {
        startActivity(new Intent(NoPredictions.this, AddEmptyItemActivity.class));
    }
}
