package com.programistka.makemyshoppinglist.start;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programistka.makemyshoppinglist.models.NewItem;
import com.programistka.makemyshoppinglist.predictions.ShowPredictionsActivity;
import com.programistka.makemyshoppinglist.presenters.DbConfig;
import com.programistka.makemyshoppinglist.wizard.WizardActivity;



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
