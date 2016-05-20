package net.programistka.shoppingadvisor.predictions.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.predictions.PredictionsAdapter;
import net.programistka.shoppingadvisor.predictions.ShowPredictionsInteractor;
import net.programistka.shoppingadvisor.predictions.ShowPredictionsPresenter;
import net.programistka.shoppingadvisor.presenters.DbConfig;

public class ThirtyDaysFragment extends Fragment{
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
        View view = inflater.inflate(R.layout.fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.fragmentItems);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        initData();
    }

    private void initData() {
        ShowPredictionsPresenter presenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig(), getContext()));
        adapter = new PredictionsAdapter(presenter.getPredictionsForMonth());
        recyclerView.setAdapter(adapter);
    }
}
