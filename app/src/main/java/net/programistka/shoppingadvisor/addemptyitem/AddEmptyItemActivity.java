package net.programistka.shoppingadvisor.addemptyitem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.predictions.ShowPredictionsActivity;
import net.programistka.shoppingadvisor.presenters.DbConfig;
import net.programistka.shoppingadvisor.selectallItems.SelectAllItemsInteractor;
import net.programistka.shoppingadvisor.selectallItems.SelectAllItemsPresenter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEmptyItemActivity extends AppCompatActivity implements AddEmptyItemView, AdapterView.OnItemClickListener {

    private AddEmptyItemPresenter addEmptyItemPresenter ;
    private Long time;

    @BindView(R.id.emptyItemName) AutoCompleteTextView emptyItemName;
    @BindView(R.id.addNewEmptyItem) Button addNewEmptyItem;
    @BindView(R.id.cancel) Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_empty_item);
        ButterKnife.bind(this);

        addEmptyItemPresenter = new AddEmptyItemPresenter(new AddEmptyItemInteractor(new DbConfig(), getApplicationContext()), this);
        SelectAllItemsPresenter selectAllItemsPresenter = new SelectAllItemsPresenter(new SelectAllItemsInteractor(new DbConfig(), getApplicationContext()));

        SuggestionsAdapter adapter = new SuggestionsAdapter(this, selectAllItemsPresenter.selectAllItemsFromItemsTable());
        emptyItemName.setAdapter(adapter);
        emptyItemName.setOnItemClickListener(this);
    }

    @Override
    public void showEmptyItemNameMessage() {
        Toast toast = Toast.makeText(this, "Name is empty", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @OnClick(R.id.addNewEmptyItem)
    public void addNewEmptyItem() {
        addEmptyItemPresenter.addNewEmptyItem(emptyItemName.getText().toString(), getCurrentTime().getTimeInMillis());
    }

    @Override
    public void showDialogToAddAnotherItem() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Item added. Do you want to add another item?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                startActivity(new Intent(getApplicationContext(), AddEmptyItemActivity.class));
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    @OnClick(R.id.cancel)
    public void cancel() {
        this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, final long l) {
        time = Calendar.getInstance().getTimeInMillis();
        addNewEmptyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmptyItemPresenter.insertExistingEmptyItem(l, time);
                showDialogToAddAnotherItem();
            }
        });
    }

    @NonNull
    private Calendar getCurrentTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }
}