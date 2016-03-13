package net.programistka.shoppingadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class AddShoppingEvent extends AppCompatActivity {

    private DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new DbHandler(this);

        final ItemsAdapter adapter = new ItemsAdapter(this, dbHandler.getItems());
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.txtItemName);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              dbHandler.addShoppingItem(adapter.getItemId(position));
            }
        });
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
