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

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.adapters.SuggestionsAdapter;
import net.programistka.shoppingadvisor.dbhandlers.EmptyItemsDbHandler;
import net.programistka.shoppingadvisor.models.Item;

import java.util.List;

public class AddEmptyItem extends AppCompatActivity {

    private EmptyItemsDbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new EmptyItemsDbHandler(this);
        List<Item> allItems = dbHandler.selectAllItemsFromItemsTable();

        final SuggestionsAdapter adapter = new SuggestionsAdapter(this, allItems);
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.txtItemName);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                Button button = (Button) findViewById(R.id.btnAddNewItem);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHandler.insertExistingEmptyItem(id);
                        redirectToEmptyItemsHistoryView();
                    }
                });
            }
        });
    }

    public void btnAddNewItemClick(View view) {
        AutoCompleteTextView itemName = (AutoCompleteTextView) findViewById(R.id.txtItemName);
        if(itemName.getText().toString().length() == 0) {
            showEmptyItemNameMessage();
        }
        else {
            createNewEmptyItem((EditText) findViewById(R.id.txtItemName));
            redirectToEmptyItemsHistoryView();
        }
    }

    private void createNewEmptyItem(EditText textField) {
        if(textField.getText() != null) {
            dbHandler.insertNewEmptyItem(textField.getText().toString());
        }
    }

    private void redirectToEmptyItemsHistoryView() {
        startActivity(new Intent(getApplicationContext(), ShowEmptyItemsHistory.class));
    }

    private void showEmptyItemNameMessage() {
        Toast toast = Toast.makeText(this, "Item name is empty", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
