package com.programistka.makemyshoppinglist.wizard.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.programistka.makemyshoppinglist.R;

import butterknife.ButterKnife;

public class SevenDaysFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wizard_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button nextStep = (Button) getActivity().findViewById(R.id.nextStep);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText item1 = (EditText) getActivity().findViewById(R.id.productName1);
                EditText item2 = (EditText) getActivity().findViewById(R.id.productName2);
                EditText item3 = (EditText) getActivity().findViewById(R.id.productName3);
                EditText item4 = (EditText) getActivity().findViewById(R.id.productName4);
                EditText item5 = (EditText) getActivity().findViewById(R.id.productName5);

                Bundle args = new Bundle();

                if (item1.getText().toString().trim().length() > 0) {
                    args.putString("item1", item1.getText().toString().trim());
                }
                if (item2.getText().toString().trim().length() > 0) {
                    args.putString("item2", item2.getText().toString().trim());
                }
                if (item3.getText().toString().trim().length() > 0) {
                    args.putString("item3", item3.getText().toString().trim());
                }
                if (item4.getText().toString().trim().length() > 0) {
                    args.putString("item4", item4.getText().toString().trim());
                }
                if (item5.getText().toString().trim().length() > 0) {
                    args.putString("item5", item5.getText().toString().trim());
                }

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Fragment myFragment = new ThirtyDaysFragment();

                myFragment.setArguments(args);
                fragmentTransaction.replace(R.id.myfragment, myFragment);

                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });
    }
}
