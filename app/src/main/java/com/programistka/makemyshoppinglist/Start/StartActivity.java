package com.programistka.makemyshoppinglist.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.programistka.makemyshoppinglist.predictions.ShowPredictionsActivity;
import com.programistka.makemyshoppinglist.presenters.DbConfig;
import com.programistka.makemyshoppinglist.wizard.WizardActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartPresenter presenter = new StartPresenter(new StartInteractor(new DbConfig(), this));
        if (presenter.ifAnyItemsExists()) {
            Intent intent = new Intent(getApplicationContext(), ShowPredictionsActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), WizardActivity.class);
            startActivity(intent);
        }
    }
}
