package com.programistka.makemyshoppinglist.predictions.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.dbhandlers.DatabaseConfigApp;
import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.predictions.PredictionsAdapter;
import com.programistka.makemyshoppinglist.predictions.ShowPredictionsInteractor;
import com.programistka.makemyshoppinglist.predictions.ShowPredictionsPresenter;

import java.util.List;

public class SevenDaysFragment extends Fragment {
    PredictionsAdapter adapter;
    RecyclerView recyclerView;

    public SevenDaysFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_7days, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = getView().findViewById(R.id.fragmentItems);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initData() {
        ShowPredictionsPresenter presenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DatabaseConfigApp()));
        List<EmptyItem> predictions = presenter.getPredictionsForWeek();
        if (predictions.size() == 0) {
            RecyclerView recyclerView = getView().findViewById(R.id.fragmentItems);
            recyclerView.setVisibility(View.INVISIBLE);
            TextView textView = getView().findViewById(R.id.allItemsArchived);
            textView.setVisibility(View.VISIBLE);
            return;
        }

//        adapter = new PredictionsAdapter(predictions);
//        recyclerView.setAdapter(adapter);
    }
}
