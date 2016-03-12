package net.programistka.shoppingadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.List;

public class AddShoppingEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DbHandler dbHandler = new DbHandler(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dbHandler.getItems());
        AutoCompleteTextView view = (AutoCompleteTextView) findViewById(R.id.txtItemName);
        view.setAdapter(adapter);
    }

    public void AddNewItem(View view) {
        DbHandler dbHandler = new DbHandler(this);
        EditText textField = (EditText) findViewById(R.id.txtItemName);
        Item newItem = new Item();
        newItem.setName(textField.getText().toString());
        dbHandler.addItem(newItem);
        Intent historyList = new Intent(getApplicationContext(), ShowHistory.class);
        startActivity(historyList);
    }
}
