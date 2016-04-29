package net.programistka.shoppingadvisor.addemptyitem;

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
import net.programistka.shoppingadvisor.acitivities.ShowEmptyItemsHistoryActivity;
import net.programistka.shoppingadvisor.adapters.SuggestionsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEmptyItemActivity extends AppCompatActivity implements AddEmptyItemView, AdapterView.OnItemClickListener {

    private AddEmptyItemPresenter presenter;

    @BindView(R.id.emptyItemName) AutoCompleteTextView emptyItemName;
    @BindView(R.id.addNewEmptyItem) Button addNewEmptyItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_empty_item);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new AddEmptyItemPresenter(this);

        SuggestionsAdapter adapter = new SuggestionsAdapter(this, presenter.selectAllItemsFromItemsTable());
        emptyItemName.setAdapter(adapter);
        emptyItemName.setOnItemClickListener(this);
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

    @OnClick(R.id.addNewEmptyItem)
    public void addNewEmptyItem() {
        presenter.addNewEmptyItem(emptyItemName.getText().toString());
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