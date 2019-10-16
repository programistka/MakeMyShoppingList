package com.programistka.makemyshoppinglist.wizard.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.programistka.makemyshoppinglist.CalendarProvider;
import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.dbhandlers.DatabaseConfig;
import com.programistka.makemyshoppinglist.dbhandlers.FirebaseDbHandler;
import com.programistka.makemyshoppinglist.predictions.ShowPredictionsActivity;

import java.util.ArrayList;
import java.util.List;

//import com.programistka.makemyshoppinglist.addemptyitem.AddEmptyItemInteractorNew;

public class ThirtyDaysFragment extends Fragment {
    private Bundle args;
    private FirebaseDbHandler firebaseDbHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDbHandler = new FirebaseDbHandler(new DatabaseConfig());
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
        loadSavedPreferences();

        Button previousStep = getActivity().findViewById(R.id.backTo7DaysWizardFragment);
        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
                getFragmentManager().popBackStack();
            }

            private void savePreferences() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                EditText item1 = getActivity().findViewById(R.id.productName1);
                EditText item2 = getActivity().findViewById(R.id.productName2);
                EditText item3 = getActivity().findViewById(R.id.productName3);
                EditText item4 = getActivity().findViewById(R.id.productName4);
                EditText item5 = getActivity().findViewById(R.id.productName5);
                String item1Text = item1.getText().toString().trim();
                String item2Text = item2.getText().toString().trim();
                String item3Text = item3.getText().toString().trim();
                String item4Text = item4.getText().toString().trim();
                String item5Text = item5.getText().toString().trim();
                if (item1Text.length() > 0) {
                    editor.putString("item1Text", item1Text);
                }
                if (item2Text.length() > 0) {
                    editor.putString("item1Text", item1Text);
                }
                if (item3Text.length() > 0) {
                    editor.putString("item1Text", item1Text);
                }
                if (item4Text.length() > 0) {
                    editor.putString("item1Text", item1Text);
                }
                if (item5Text.length() > 0) {
                    editor.putString("item1Text", item1Text);
                }
                editor.commit();
            }

            private void saveSharedPreferences() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                EditText item1 = getActivity().findViewById(R.id.productName1);
                EditText item2 = getActivity().findViewById(R.id.productName2);
                EditText item3 = getActivity().findViewById(R.id.productName3);
                EditText item4 = getActivity().findViewById(R.id.productName4);
                EditText item5 = getActivity().findViewById(R.id.productName5);
                String item1Text = item1.getText().toString().trim();
                String item2Text = item2.getText().toString().trim();
                String item3Text = item3.getText().toString().trim();
                String item4Text = item4.getText().toString().trim();
                String item5Text = item5.getText().toString().trim();
                if (item1Text.length() > 0) {
                    editor.putString("item1Text", item1Text);
                }
                if (item2Text.length() > 0) {
                    editor.putString("item2Text", item1Text);
                }
                if (item3Text.length() > 0) {
                    editor.putString("item3Text", item1Text);
                }
                if (item4Text.length() > 0) {
                    editor.putString("item4Text", item1Text);
                }
                if (item5Text.length() > 0) {
                    editor.putString("item5Text", item1Text);
                }
                editor.commit();
            }


        });

        final Button nextStep = getActivity().findViewById(R.id.finish);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                nextStep.setEnabled(false);
                nextStep.setClickable(false);

                EditText item1 = getActivity().findViewById(R.id.productName1);
                EditText item2 = getActivity().findViewById(R.id.productName2);
                EditText item3 = getActivity().findViewById(R.id.productName3);
                EditText item4 = getActivity().findViewById(R.id.productName4);
                EditText item5 = getActivity().findViewById(R.id.productName5);

                List<String> thirtyDaysItems = new ArrayList<>();

                String item1Text = item1.getText().toString().trim();
                String item2Text = item2.getText().toString().trim();
                String item3Text = item3.getText().toString().trim();
                String item4Text = item4.getText().toString().trim();
                String item5Text = item5.getText().toString().trim();

                if (item1Text.length() > 0) {
                    thirtyDaysItems.add(item1Text);
                }
                if (item2Text.length() > 0) {
                    thirtyDaysItems.add(item2Text);
                }
                if (item3Text.length() > 0) {
                    thirtyDaysItems.add(item3Text);
                }
                if (item4Text.length() > 0) {
                    thirtyDaysItems.add(item4Text);
                }
                if (item5Text.length() > 0) {
                    thirtyDaysItems.add(item5Text);
                }

                List<String> sevenDaysItems = new ArrayList<>();
                if (args.getString("item1") != null && args.getString("item1").trim().length() > 0) {
                    sevenDaysItems.add(args.getString("item1"));
                }
                if (args.getString("item2") != null && args.getString("item2").trim().length() > 0) {
                    sevenDaysItems.add(args.getString("item2"));
                }
                if (args.getString("item3") != null && args.getString("item3").trim().length() > 0) {
                    sevenDaysItems.add(args.getString("item3"));
                }
                if (args.getString("item4") != null && args.getString("item4").trim().length() > 0) {
                    sevenDaysItems.add(args.getString("item4"));
                }
                if (args.getString("item5") != null && args.getString("item5").trim().length() > 0) {
                    sevenDaysItems.add(args.getString("item5"));
                }

                SaveSevenDaysItems(sevenDaysItems);
                SaveThirtyDaysItems(thirtyDaysItems);
                if (sevenDaysItems.size() > 0 || thirtyDaysItems.size() > 0) {
                    Intent intent = new Intent(getActivity(), ShowPredictionsActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getContext(), R.string.fillAnyFields, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        EditText item1 = getActivity().findViewById(R.id.productName1);
        EditText item2 = getActivity().findViewById(R.id.productName2);
        EditText item3 = getActivity().findViewById(R.id.productName3);
        EditText item4 = getActivity().findViewById(R.id.productName4);
        EditText item5 = getActivity().findViewById(R.id.productName5);
        item1.setText(sharedPreferences.getString("item1Text", ""));
        item2.setText(sharedPreferences.getString("item2Text", ""));
        item3.setText(sharedPreferences.getString("item3Text", ""));
        item4.setText(sharedPreferences.getString("item4Text", ""));
        item5.setText(sharedPreferences.getString("item5Text", ""));

    }

    private void SaveThirtyDaysItems(List<String> items) {
        for (String item : items) {
            firebaseDbHandler.addEmptyItemOnWizard(item, CalendarProvider.setNowCalendar().getTimeInMillis() - (long) 45 * 1000 * 3600 * 24, CalendarProvider.setNowCalendar().getTimeInMillis() - (long) 15 * 1000 * 3600 * 24, 30);
            //AddEmptyItemInteractor interactor = new AddEmptyItemInteractor(new DbConfig(), getActivity());
//            AddEmptyItemInteractorNew interactorNew = new AddEmptyItemInteractorNew(new DbConfig(), getActivity());
            //interactor.insertNewEmptyItemWithHistoryAndPrediction(item, CalendarProvider.setNowCalendar().getTimeInMillis() - (long) 45 * 1000 * 3600 * 24, CalendarProvider.setNowCalendar().getTimeInMillis() - (long) 15 * 1000 * 3600 * 24, 30);
//            interactorNew.insertNewEmptyItem(item, CalendarProvider.setNowCalendar().getTimeInMillis());

        }
    }

    private void SaveSevenDaysItems(List<String> items) {
        for (String item : items) {
            //AddEmptyItemInteractor interactor = new AddEmptyItemInteractor(new DbConfig(), getActivity());
            firebaseDbHandler.addEmptyItemOnWizard(item, CalendarProvider.setNowCalendar().getTimeInMillis() - (long) 10 * 1000 * 3600 * 24, CalendarProvider.setNowCalendar().getTimeInMillis() - (long) 3 * 1000 * 3600 * 24, 7);
//            AddEmptyItemInteractorNew interactorNew = new AddEmptyItemInteractorNew(new DbConfig(), getActivity());
            //interactor.insertNewEmptyItemWithHistoryAndPrediction(item, CalendarProvider.setNowCalendar().getTimeInMillis() - (long) 10 * 1000 * 3600 * 24, CalendarProvider.setNowCalendar().getTimeInMillis() - (long) 3 * 1000 * 3600 * 24, 7);
//            interactorNew.insertNewEmptyItem(item, CalendarProvider.setNowCalendar().getTimeInMillis());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        args = getArguments();
    }
}
