package net.programistka.shoppingadvisor.predictions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.presenters.DbConfig;

public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragmentItems);
        PredictionsAdapter adapter;
        ShowPredictionsPresenter presenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig(), getContext()));

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                adapter = new PredictionsAdapter(presenter.getPredictions());
                recyclerView.setAdapter(adapter);
                break;
            case 2:
                adapter = new PredictionsAdapter(presenter.getPredictionsForWeek());
                recyclerView.setAdapter(adapter);
                break;
            case 3:
                adapter = new PredictionsAdapter(presenter.getPredictionsForMonth());
                recyclerView.setAdapter(adapter);
                break;
        }
        return rootView;
    }
}
