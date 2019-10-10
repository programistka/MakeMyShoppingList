package com.programistka.makemyshoppinglist.predictions.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.models.EmptyItemPredictionEntry;
import com.programistka.makemyshoppinglist.models.NewItem;
import com.programistka.makemyshoppinglist.predictions.PredictionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllFragment extends Fragment {
    PredictionsAdapter adapter;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    ChildEventListener postListener;
    private DatabaseReference itemsStorageReference = firebaseDatabase.getReference().child("items");
    private DatabaseReference emptyItemsPredictionsStorageReference = firebaseDatabase.getReference().child("emptyItemsPredictions");

    public AllFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
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
        final List<NewItem> items = new ArrayList<>();
        postListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final EmptyItemPredictionEntry emptyItemPredictionEntry = dataSnapshot.getValue(EmptyItemPredictionEntry.class);
                itemsStorageReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot item: dataSnapshot.getChildren()) {
                            if(item.getKey().equals(emptyItemPredictionEntry.getId())) {
                                NewItem emptyItem = item.getValue(NewItem.class);
                                emptyItem.setNextEmptyItemDate(emptyItemPredictionEntry.getNextEmptyItemDate());
                                emptyItem.setId(item.getKey());
                                items.add(emptyItem);
                                adapter = new PredictionsAdapter(items);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final EmptyItemPredictionEntry emptyItemPredictionEntry = dataSnapshot.getValue(EmptyItemPredictionEntry.class);
                itemsStorageReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        items.clear();
                        for(DataSnapshot item: dataSnapshot.getChildren()) {
                            NewItem emptyItem = item.getValue(NewItem.class);
                            if(item.getKey().equals(emptyItemPredictionEntry.getId())) {
                                emptyItem.setNextEmptyItemDate(emptyItemPredictionEntry.getNextEmptyItemDate());
                            }
                            items.add(emptyItem);
                            adapter = new PredictionsAdapter(items);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        emptyItemsPredictionsStorageReference.addChildEventListener(postListener);


        // initData();
    }

    private void initData() {
//        ShowPredictionsPresenter presenter = new ShowPredictionsPresenter(new ShowPredictionsInteractor(new DbConfig(), getContext()));
//        List<EmptyItem> predictions = presenter.getPredictions();
//        if (predictions.size() == 0) {
//            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.fragmentItems);
//            recyclerView.setVisibility(View.INVISIBLE);
//            TextView textView = (TextView) getView().findViewById(R.id.allItemsArchived);
//            textView.setVisibility(View.VISIBLE);
//            return;
//        }

//        adapter = new PredictionsAdapter(predictions);
//        recyclerView.setAdapter(adapter);
    }
}
