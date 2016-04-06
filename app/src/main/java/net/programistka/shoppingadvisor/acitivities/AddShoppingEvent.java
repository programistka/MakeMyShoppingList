package net.programistka.shoppingadvisor.acitivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.programistka.shoppingadvisor.DbHandler;
import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.adapters.SuggestionsAdapter;
import net.programistka.shoppingadvisor.models.Item;

public class AddShoppingEvent extends AppCompatActivity {

    private DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new DbHandler(this);

        final SuggestionsAdapter adapter = new SuggestionsAdapter(this, dbHandler.getSuggestionsItems());
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.txtItemName);
        final Toast toast = Toast.makeText(this, "Item name is empty", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                Button button = (Button) findViewById(R.id.btnAddNewItem);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(autoCompleteTextView.getText().toString().length() == 0) {
                            toast.show();
                        }
                        dbHandler.addShoppingItem(id);
                    }
                });
            }
        });
    }

    public void AddNewItem(View view) {
        AutoCompleteTextView itemName = (AutoCompleteTextView) findViewById(R.id.txtItemName);
        if(itemName.getText().toString().length() == 0) {
            Toast toast = Toast.makeText(this, "Item name is empty", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            DbHandler dbHandler = new DbHandler(this);
            EditText textField = (EditText) findViewById(R.id.txtItemName);
            Item newItem = new Item();
            newItem.setName(textField.getText().toString());
            dbHandler.addItem(newItem);
            Intent historyList = new Intent(getApplicationContext(), ShowHistory.class);
            startActivity(historyList);
        }
    }
}
