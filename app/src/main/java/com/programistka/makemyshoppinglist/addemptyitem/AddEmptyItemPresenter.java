package com.programistka.makemyshoppinglist.addemptyitem;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programistka.makemyshoppinglist.models.EmptyItemHistoryEntry;
import com.programistka.makemyshoppinglist.models.EmptyItemPredictionEntry;
import com.programistka.makemyshoppinglist.models.HistoryEntryNew;
import com.programistka.makemyshoppinglist.models.NewItem;
import com.programistka.makemyshoppinglist.models.Prediction;
import com.programistka.makemyshoppinglist.models.PredictionsHandler;

import java.util.ArrayList;
import java.util.List;

public class AddEmptyItemPresenter {

    private AddEmptyItemInteractor interactor;
    private AddEmptyItemView view;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference itemsStorageReference = firebaseDatabase.getReference().child("items");
    private DatabaseReference emptyItemsHistoryStorageReference = firebaseDatabase.getReference().child("emptyItemsHistory");
    private DatabaseReference emptyItemsPredictionsStorageReference = firebaseDatabase.getReference().child("emptyItemsPredictions");


    public AddEmptyItemPresenter(AddEmptyItemInteractor interactor, AddEmptyItemView view) {
        this.interactor = interactor;
        this.view = view;
    }

    public long insertNewEmptyItem(String name, long time) {
        return interactor.insertNewEmptyItem(name, time);
    }

    public void insertExistingEmptyItem(long id, long time) {
        interactor.insertExistingEmptyItem(id, time);
    }

    public void insertNewEmptyItemWithHistoryAndPrediction(String name, long time1, long time2, int daysToRunOut) {
        interactor.insertNewEmptyItemWithHistoryAndPrediction(name, time1, time2, daysToRunOut);
    }

    public void addNewEmptyItem(String name, long time) {
        if (name.length() == 0) {
            view.showEmptyItemNameMessage();
        } else {
            if (insertNewEmptyItem(name, time) > 0) {
                view.showDialogToAddAnotherItem();
            } else {
                view.showAddingErrorMessage();
            }
        }
    }

    public void addNewEmptyItemNEW(String name, final long time) {
        if (name.length() == 0) {
            view.showEmptyItemNameMessage();
        } else {
            final NewItem newItem = new NewItem(name.toLowerCase().trim());
            itemsStorageReference.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // if element with that name already exists then add the history entry and update predictions
                    if(dataSnapshot.getChildren().iterator().hasNext()){
                        final String elementId = dataSnapshot.getChildren().iterator().next().getKey();
                        final EmptyItemHistoryEntry emptyItemHistoryEntry1 = new EmptyItemHistoryEntry(elementId, time);
                        emptyItemsHistoryStorageReference.push().setValue(emptyItemHistoryEntry1);
                        emptyItemsHistoryStorageReference.orderByChild("id").equalTo(elementId).addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<Long> emptyTimes = new ArrayList<>();
                                Iterable<DataSnapshot> historyEntries = dataSnapshot.getChildren();
                                for(DataSnapshot entry: historyEntries) {
                                    HistoryEntryNew historyEntry = entry.getValue(HistoryEntryNew.class);
                                    emptyTimes.add(historyEntry.getDate());
                                }
                                if (emptyTimes.size() > 1) {
                                    Prediction prediction = PredictionsHandler.generatePrediction(emptyTimes);
                                    final EmptyItemPredictionEntry emptyItemPredictionEntry = new EmptyItemPredictionEntry(elementId, prediction.getTime(), prediction.getDaysNumber());

                                    emptyItemsPredictionsStorageReference.orderByChild("id").equalTo(elementId).addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           if (dataSnapshot.getChildrenCount() > 0) {
                                               for(DataSnapshot prediction : dataSnapshot.getChildren()) {
                                                   EmptyItemPredictionEntry entry = prediction.getValue(EmptyItemPredictionEntry.class);
                                                   if(entry.getId().equals(elementId)) {
                                                       emptyItemsPredictionsStorageReference.child(prediction.getKey()).setValue(emptyItemPredictionEntry);
                                                   }
                                               }
                                           }
                                           else {
                                              emptyItemsPredictionsStorageReference.push().setValue(emptyItemPredictionEntry);
                                           }
                                       }

                                       @Override
                                       public void onCancelled(@NonNull DatabaseError databaseError) {

                                       }
                                   });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    // if element with that name not yet exists add it to the items table and add the history entry
                    else {
                        DatabaseReference element = itemsStorageReference.push();
                        // commented out to avoid duplicates
                        String newItemId = element.getKey();
                        element.setValue(newItem);
                        if(newItemId != null) {
                            EmptyItemHistoryEntry emptyItemHistoryEntry1 = new EmptyItemHistoryEntry(newItemId, time);
                            emptyItemsHistoryStorageReference.push().setValue(emptyItemHistoryEntry1);
                        }
                    }
                    view.showDialogToAddAnotherItem();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    view.showAddingErrorMessage();
                }
            });
        }
    }
}
