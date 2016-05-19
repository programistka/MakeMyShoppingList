package net.programistka.shoppingadvisor.wizard;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.programistka.shoppingadvisor.CalendarProvider;
import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemInteractor;
import net.programistka.shoppingadvisor.addemptyitem.AddEmptyItemPresenter;
import net.programistka.shoppingadvisor.predictions.ShowPredictionsActivity;
import net.programistka.shoppingadvisor.presenters.DbConfig;

import java.util.ArrayList;
import java.util.List;

public class ThirtyDaysFragment extends Fragment {
    private Bundle args;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Wizard - Step 2");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wizard_fragment_30days, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button nextStep = (Button) getActivity().findViewById(R.id.finish);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText item1 = (EditText) getActivity().findViewById(R.id.productName1);
                EditText item2 = (EditText) getActivity().findViewById(R.id.productName2);
                EditText item3 = (EditText) getActivity().findViewById(R.id.productName3);
                EditText item4 = (EditText) getActivity().findViewById(R.id.productName4);
                EditText item5 = (EditText) getActivity().findViewById(R.id.productName5);

                List<String> items = new ArrayList<>();

                if(item1.getText().toString().trim() != "") {
                    items.add(item1.getText().toString().trim());
                }
                if(item2.getText().toString().trim() != "") {
                    items.add(item1.getText().toString().trim());
                }
                if(item3.getText().toString().trim() != "") {
                    items.add(item1.getText().toString().trim());
                }
                if(item4.getText().toString().trim() != "") {
                    items.add(item1.getText().toString().trim());
                }
                if(item5.getText().toString().trim() != "") {
                    items.add(item1.getText().toString().trim());
                }

                SaveSevenDaysItems(args);
                SaveThirtyDaysItems(items);

                Intent intent = new Intent(getActivity(), ShowPredictionsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SaveThirtyDaysItems(List<String> items) {
        for(String item : items) {
            AddEmptyItemInteractor interactor = new AddEmptyItemInteractor(new DbConfig(), getActivity());
            interactor.insertNewEmptyItemAndPrediction(item, CalendarProvider.setNowCalendar().getTimeInMillis(), 7);
        }
    }

    private void SaveSevenDaysItems(Bundle args) {
//        for(String item : args) {
//            AddEmptyItemInteractor interactor = new AddEmptyItemInteractor(new DbConfig(), getActivity());
//            interactor.insertNewEmptyItem(item, CalendarProvider.setNowCalendar().getTimeInMillis());
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        args = getArguments();
    }
}