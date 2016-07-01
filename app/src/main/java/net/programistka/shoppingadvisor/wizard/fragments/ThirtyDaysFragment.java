package net.programistka.shoppingadvisor.wizard.fragments;

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
import net.programistka.shoppingadvisor.wizard.WizardActivity;

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
    public void onActivityCreated(final Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        Button previousStep = (Button) getActivity().findViewById(R.id.backTo7DaysWizardFragment);
        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack();
            }
        });

        Button nextStep = (Button) getActivity().findViewById(R.id.finish);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().beginTransaction().addToBackStack("Test").commit();

                EditText item1 = (EditText) getActivity().findViewById(R.id.productName1);
                EditText item2 = (EditText) getActivity().findViewById(R.id.productName2);
                EditText item3 = (EditText) getActivity().findViewById(R.id.productName3);
                EditText item4 = (EditText) getActivity().findViewById(R.id.productName4);
                EditText item5 = (EditText) getActivity().findViewById(R.id.productName5);

                List<String> thirtyDaysItems = new ArrayList<>();

                if(item1.getText().toString().trim().length() > 0) {
                    thirtyDaysItems.add(item1.getText().toString().trim());
                }
                if(item2.getText().toString().trim().length() > 0) {
                    thirtyDaysItems.add(item2.getText().toString().trim());
                }
                if(item3.getText().toString().trim().length() > 0) {
                    thirtyDaysItems.add(item3.getText().toString().trim());
                }
                if(item4.getText().toString().trim().length() > 0) {
                    thirtyDaysItems.add(item4.getText().toString().trim());
                }
                if(item5.getText().toString().trim().length() > 0) {
                    thirtyDaysItems.add(item5.getText().toString().trim());
                }

                List<String> sevenDaysItems = new ArrayList<>();
                if(args.getString("item1")!= null && args.getString("item1").trim().length() > 0 ){
                    sevenDaysItems.add(args.getString("item1"));
                }
                if(args.getString("item2")!= null && args.getString("item2").trim().length() > 0 ){
                    sevenDaysItems.add(args.getString("item2"));
                }
                if(args.getString("item3")!= null && args.getString("item3").trim().length() > 0 ){
                    sevenDaysItems.add(args.getString("item3"));
                }
                if(args.getString("item4")!= null && args.getString("item4").trim().length() > 0 ){
                    sevenDaysItems.add(args.getString("item4"));
                }
                if(args.getString("item5")!= null && args.getString("item5").trim().length() > 0 ){
                    sevenDaysItems.add(args.getString("item5"));
                }

                SaveSevenDaysItems(sevenDaysItems);
                SaveThirtyDaysItems(thirtyDaysItems);
                if(sevenDaysItems.size() > 0 && thirtyDaysItems.size() > 0) {
                    Intent intent = new Intent(getActivity(), ShowPredictionsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void SaveThirtyDaysItems(List<String> items) {
        for(String item : items) {
            AddEmptyItemInteractor interactor = new AddEmptyItemInteractor(new DbConfig(), getActivity());
            interactor.insertNewEmptyItemWithHistoryAndPrediction(item, CalendarProvider.setNowCalendar().getTimeInMillis() - (long)45*1000*3600*24, CalendarProvider.setNowCalendar().getTimeInMillis() - (long)15*1000*3600*24, 30);
        }
    }

    private void SaveSevenDaysItems(List<String> items) {
        for(String item : items) {
            AddEmptyItemInteractor interactor = new AddEmptyItemInteractor(new DbConfig(), getActivity());
            interactor.insertNewEmptyItemWithHistoryAndPrediction(item, CalendarProvider.setNowCalendar().getTimeInMillis() - (long)10*1000*3600*24, CalendarProvider.setNowCalendar().getTimeInMillis() - (long)3*1000*3600*24, 7);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        args = getArguments();
    }
}
