package com.programistka.makemyshoppinglist.predictions.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.predictions.PredictionsAdapter;
import com.programistka.makemyshoppinglist.predictions.ShowPredictionsInteractor;
import com.programistka.makemyshoppinglist.predictions.ShowPredictionsPresenter;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

import java.util.List;

public class ThirtyDaysFragment extends Fragment {
    PredictionsAdapter adapter;
    RecyclerView recyclerView;

    public ThirtyDaysFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_30days, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
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

    private void initData() {
        ShowPredictionsPresenter presenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig(), getContext()));
        List<EmptyItem> predictions = presenter.getPredictionsForMonth();
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
