package com.programistka.makemyshoppinglist.dbhandlers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programistka.makemyshoppinglist.addemptyitem.AddEmptyItemView;
import com.programistka.makemyshoppinglist.models.EmptyItemHistoryEntry;
import com.programistka.makemyshoppinglist.models.EmptyItemPredictionEntry;
import com.programistka.makemyshoppinglist.models.HistoryEntryNew;
import com.programistka.makemyshoppinglist.models.NewItem;
import com.programistka.makemyshoppinglist.models.Prediction;
import com.programistka.makemyshoppinglist.models.PredictionsHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FirebaseDbHandler {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference itemsStorageReference = firebaseDatabase.getReference().child("items");
    private DatabaseReference emptyItemsHistoryStorageReference = firebaseDatabase.getReference().child("emptyItemsHistory");
    private DatabaseReference emptyItemsPredictionsStorageReference = firebaseDatabase.getReference().child("emptyItemsPredictions");
    private DatabaseReference emptyItemsArchiveStorageReference = firebaseDatabase.getReference().child("emptyItemsArchive");

    public void addEmptyItem(final AddEmptyItemView view, String name, final long time) {
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
                    //TODO: handle in view view.showAddingErrorMessage();
                }
            });
         }
    }

    public void addEmptyExistingItem(final String itemId, final long time) {

        final EmptyItemHistoryEntry emptyItemHistoryEntry = new EmptyItemHistoryEntry(itemId, time);
        emptyItemsHistoryStorageReference.push().setValue(emptyItemHistoryEntry);
        emptyItemsHistoryStorageReference.orderByChild("id").equalTo(itemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Long> emptyTimes = new ArrayList<>();
                Iterable<DataSnapshot> historyEntries = dataSnapshot.getChildren();
                for (DataSnapshot entry : historyEntries) {
                    HistoryEntryNew historyEntry = entry.getValue(HistoryEntryNew.class);
                    emptyTimes.add(historyEntry.getDate());
                }
                if (emptyTimes.size() > 1) {
                    Prediction prediction = PredictionsHandler.generatePrediction(emptyTimes);
                    final EmptyItemPredictionEntry emptyItemPredictionEntry = new EmptyItemPredictionEntry(itemId, prediction.getTime(), prediction.getDaysNumber());

                    emptyItemsPredictionsStorageReference.orderByChild("id").equalTo(itemId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                for (DataSnapshot prediction : dataSnapshot.getChildren()) {
                                    EmptyItemPredictionEntry entry = prediction.getValue(EmptyItemPredictionEntry.class);
                                    if (entry.getId().equals(itemId)) {
                                        emptyItemsPredictionsStorageReference.child(prediction.getKey()).setValue(emptyItemPredictionEntry);
                                    }
                                }
                            } else {
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

    public void addEmptyItemOnWizard(String name, long time1, long time2, int daysToRunOut) {
        final NewItem newItem = new NewItem(name.toLowerCase().trim());
        DatabaseReference element = itemsStorageReference.push();
        String newItemId = element.getKey();
        element.setValue(newItem);
        if(newItemId != null) {
            EmptyItemHistoryEntry emptyItemHistoryEntry1 = new EmptyItemHistoryEntry(newItemId, time1);
            EmptyItemHistoryEntry emptyItemHistoryEntry2 = new EmptyItemHistoryEntry(newItemId, time2);
            emptyItemsHistoryStorageReference.push().setValue(emptyItemHistoryEntry1);
            emptyItemsHistoryStorageReference.push().setValue(emptyItemHistoryEntry2);
            EmptyItemPredictionEntry emptyItemPredictionEntry = new EmptyItemPredictionEntry();
            emptyItemPredictionEntry.setId(newItemId);
            emptyItemPredictionEntry.setDaysToRunOut(daysToRunOut);
            emptyItemPredictionEntry.setNextEmptyItemDate(time2 + (long) daysToRunOut * 1000 * 3600 * 24);
            emptyItemsPredictionsStorageReference.push().setValue(emptyItemPredictionEntry);
        }
    }

    public void addBoughtItem(final String itemId) {
        emptyItemsPredictionsStorageReference.orderByChild("id").equalTo(itemId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot prediction : dataSnapshot.getChildren()) {
                        EmptyItemPredictionEntry entry = prediction.getValue(EmptyItemPredictionEntry.class);
                        Calendar c = Calendar.getInstance();
                        EmptyItemHistoryEntry emptyItemPredictionEntry = new EmptyItemHistoryEntry();
                        emptyItemPredictionEntry.setId(itemId);
                        if (entry.getNextEmptyItemDate() < c.getTimeInMillis()) {
                            emptyItemPredictionEntry.setDate(PredictionsHandler.generateExpiredBoughtPrediction((int) entry.getDaysToRunOut()).getTime());
                        } else {
                            emptyItemPredictionEntry.setDate(PredictionsHandler.generateBoughtPrediction(entry.getNextEmptyItemDate(), (int) entry.getDaysToRunOut()).getTime());
                        }
                        emptyItemsPredictionsStorageReference.child(prediction.getKey()).setValue(emptyItemPredictionEntry);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void moveItemToArchive(final String itemId) {
        itemsStorageReference.child(itemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    NewItem newItem = dataSnapshot.getValue(NewItem.class);
                    newItem.setArchived(true);
                    itemsStorageReference.child(itemId).setValue(newItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
