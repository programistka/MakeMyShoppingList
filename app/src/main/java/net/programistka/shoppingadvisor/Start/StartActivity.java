package net.programistka.shoppingadvisor.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;

import net.programistka.shoppingadvisor.predictions.ShowPredictionsActivity;
import net.programistka.shoppingadvisor.presenters.DbConfig;
import net.programistka.shoppingadvisor.wizard.WizardActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartPresenter presenter = new StartPresenter(new StartInteractor(new DbConfig(), this));
        if(presenter.ifAnyItemsExists()) {
            Intent intent = new Intent(getApplicationContext(), ShowPredictionsActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), WizardActivity.class);
            startActivity(intent);
        }
    }
}
