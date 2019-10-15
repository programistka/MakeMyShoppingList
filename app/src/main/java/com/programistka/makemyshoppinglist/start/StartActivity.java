package com.programistka.makemyshoppinglist.start;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.programistka.makemyshoppinglist.predictions.ShowPredictionsActivity;



public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), ShowPredictionsActivity.class);
        startActivity(intent);

//        StartPresenter presenter = new StartPresenter(new StartInteractor(new DbConfig(), this));
//        if (presenter.ifAnyItemsExists()) {
//            Intent intent = new Intent(getApplicationContext(), ShowPredictionsActivity.class);
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(getApplicationContext(), WizardActivity.class);
//            startActivity(intent);
//        }
    }
}
