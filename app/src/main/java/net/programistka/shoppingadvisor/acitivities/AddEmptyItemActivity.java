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
import android.widget.Toast;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.adapters.SuggestionsAdapter;
import net.programistka.shoppingadvisor.presenters.EmptyItemsPresenter;
import net.programistka.shoppingadvisor.views.EmptyItemsView;

public class AddEmptyItemActivity extends AppCompatActivity implements EmptyItemsView, AdapterView.OnItemClickListener, View.OnClickListener {

    private AutoCompleteTextView emptyItemName;
    private Button addNewEmptyItem;
    private EmptyItemsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_empty_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emptyItemName = (AutoCompleteTextView) findViewById(R.id.emptyItemName);
        addNewEmptyItem = (Button) findViewById(R.id.addNewEmptyItem);
        addNewEmptyItem.setOnClickListener(this);
        presenter = new EmptyItemsPresenter(this);

        SuggestionsAdapter adapter = new SuggestionsAdapter(this, presenter.selectAllItemsFromItemsTable());
        this.emptyItemName.setAdapter(adapter);
        this.emptyItemName.setOnItemClickListener(this);
    }

    @Override
    public void redirectToEmptyItemsHistoryView() {
        startActivity(new Intent(getApplicationContext(), ShowEmptyItemsHistoryActivity.class));
    }

    @Override
    public void showEmptyItemNameMessage() {
        Toast toast = Toast.makeText(this, "EmptyItem name is empty", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        String emptyItemNameText = emptyItemName.getText().toString();
        if(emptyItemNameText.length() == 0) {
            showEmptyItemNameMessage();
        }
        else {
            presenter.insertNewEmptyItem(emptyItemNameText);
            redirectToEmptyItemsHistoryView();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, final long l) {
        addNewEmptyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.insertExistingEmptyItem(l);
                redirectToEmptyItemsHistoryView();
            }
        });
    }
}