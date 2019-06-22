package com.programistka.makemyshoppinglist.predictions.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.models.EmptyItemNew;
import com.programistka.makemyshoppinglist.predictions.PredictionsAdapter;
import com.programistka.makemyshoppinglist.predictions.PredictionsAdapterNew;
import com.programistka.makemyshoppinglist.predictions.ShowPredictionsInteractor;
import com.programistka.makemyshoppinglist.predictions.ShowPredictionsPresenter;
import com.programistka.makemyshoppinglist.presenters.DbConfig;

import java.util.ArrayList;
import java.util.List;

public class AllFragmentNew extends Fragment {
    PredictionsAdapterNew adapter;
    ListView listView;
    ChildEventListener mChildEventListener;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference emptyItemsDatabaseReference = firebaseDatabase.getReference().child("emptyItemsPredictions");

    public AllFragmentNew() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachDatabaseReadListener();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_new, container, false);
        listView = (ListView) view.findViewById(R.id.fragmentItems_new);
        List<EmptyItemNew> predictions = new ArrayList<>();
        adapter = new PredictionsAdapterNew(getActivity(), R.layout.card_view, predictions);
        listView.setAdapter(adapter);
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

//        recyclerView = (RecyclerView) getView().findViewById(R.id.fragmentItems_new);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(llm);
//        initData();
    }

    private void initData() {
        ShowPredictionsPresenter presenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig(), getContext()));
//        List<EmptyItemNew> predictions = presenter.getPredictions();
//        if (predictions.size() == 0) {
//            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.fragmentItems_new);
//            recyclerView.setVisibility(View.INVISIBLE);
//            TextView textView = (TextView) getView().findViewById(R.id.allItemsArchived);
//            textView.setVisibility(View.VISIBLE);
//            return;
//        }


        //recyclerView.setAdapter(adapter);
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    EmptyItemNew friendlyMessage = dataSnapshot.getValue(EmptyItemNew.class);
                    adapter.add(friendlyMessage);
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            emptyItemsDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }
}
