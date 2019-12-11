package com.programistka.makemyshoppinglist.dbhandlers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
import java.util.Optional;
import java.util.stream.Collectors;

public class FirebaseDbHandler {
    private static DatabaseReference itemsStorageReference;
    private static DatabaseReference emptyItemsHistoryStorageReference;
    private static DatabaseReference emptyItemsPredictionsStorageReference;

    private static FirebaseDbHandler instance;

    private ArrayList<NewItem> items = new ArrayList<>();
    private ArrayList<HistoryEntryNew> historyEntries = new ArrayList<>();
    private ArrayList<EmptyItemPredictionEntry> predictionEntries = new ArrayList<>();

    public static FirebaseDbHandler init(DatabaseConfig config) {
        if(instance == null){
            instance = new FirebaseDbHandler(config);
        }
        return instance;
    }

    public FirebaseDbHandler(DatabaseConfig config) {
        itemsStorageReference = config.getItems();
        emptyItemsHistoryStorageReference = config.getHistory();
        emptyItemsPredictionsStorageReference = config.getPredictions();

        itemsStorageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()) {
                    NewItem newItem = item.getValue(NewItem.class);
                    newItem.setId(item.getKey());
                    items.add(newItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        emptyItemsHistoryStorageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historyEntries.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()) {
                    historyEntries.add(item.getValue(HistoryEntryNew.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        emptyItemsPredictionsStorageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                predictionEntries.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()) {
                    EmptyItemPredictionEntry entry = item.getValue(EmptyItemPredictionEntry.class);
                    entry.setKey(item.getKey());
                    predictionEntries.add(entry);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        emptyItemsHistoryStorageReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                HistoryEntryNew newHistoryEntry = dataSnapshot.getValue(HistoryEntryNew.class);
                Log.wtf("DEBUGING-JAKIS KEY", String.valueOf(newHistoryEntry.getDate()));
                Optional<HistoryEntryNew> entry =  historyEntries.stream().filter(item -> item.getId().equals(newHistoryEntry.getId())).findFirst();
                if(entry.isPresent()) {
                    List<Long> emptyTimes = historyEntries.stream().filter(item -> item.getId().equals(newHistoryEntry.getId())).map(item -> item.getDate()).collect(Collectors.toList());
                    if(emptyTimes.contains(newHistoryEntry.getDate())) {
                        return;
                    }
                    emptyTimes.add(newHistoryEntry.getDate());
                    Log.wtf("DEBUGING-emptytimes", String.valueOf(emptyTimes.size()));
                    Prediction prediction = PredictionsHandler.generatePrediction(emptyTimes);
                    EmptyItemPredictionEntry newPrediction = new EmptyItemPredictionEntry(newHistoryEntry.getId(), prediction.getTime(), prediction.getDaysNumber());
                    Optional<EmptyItemPredictionEntry> predictionEntry = predictionEntries.stream().filter(item -> item.getId().equals(newHistoryEntry.getId())).findFirst();
                    if(predictionEntry.isPresent()) {
                        Log.wtf("DEBUGING-emptytimes", String.valueOf(predictionEntry.get().getKey()));
                        emptyItemsPredictionsStorageReference.child(predictionEntry.get().getKey()).setValue(newPrediction);
                    }
                    else {
                        EmptyItemPredictionEntry emptyItemPredictionEntry = new EmptyItemPredictionEntry(newHistoryEntry.getId(), prediction.getTime(), prediction.getDaysNumber());
                        emptyItemsPredictionsStorageReference.push().setValue(emptyItemPredictionEntry);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
        });
    }

    public String addEmptyItem2(final AddEmptyItemView view, String name, final long time) {
        if (name.length() == 0) {
            view.showEmptyItemNameMessage();
        } else {
            Optional<NewItem> existingItem = items.stream().filter(item -> item.getName().equals(name.trim())).findFirst();
            //if element already exists
            if(existingItem.isPresent()){
                EmptyItemHistoryEntry emptyItemHistoryEntry = new EmptyItemHistoryEntry(existingItem.get().getId(), time);
                emptyItemsHistoryStorageReference.push().setValue(emptyItemHistoryEntry);
                return emptyItemHistoryEntry.getId();
            }
            //if element is brand new
            else {
                NewItem newItem = new NewItem(name.toLowerCase().trim());
                DatabaseReference element = itemsStorageReference.push();
                String newItemId = element.getKey();
                element.setValue(newItem);
                EmptyItemHistoryEntry emptyItemHistoryEntry = new EmptyItemHistoryEntry(newItemId, time);
                emptyItemsHistoryStorageReference.push().setValue(emptyItemHistoryEntry);
                return newItemId;
            }
        }
        return null;
    }

    public void addEmptyExistingItem2(final String itemId, final long time) {
        final EmptyItemHistoryEntry emptyItemHistoryEntry = new EmptyItemHistoryEntry(itemId, time);
        emptyItemsHistoryStorageReference.push().setValue(emptyItemHistoryEntry);
    }

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
        Log.d("DEBUGING-add", itemId);
        final EmptyItemHistoryEntry emptyItemHistoryEntry = new EmptyItemHistoryEntry(itemId, time);
        emptyItemsHistoryStorageReference.push().setValue(emptyItemHistoryEntry);
        emptyItemsHistoryStorageReference.orderByChild("id").equalTo(itemId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Long> emptyTimes = new ArrayList<>();
                Log.d("DEBUGING-dziadostwo", String.valueOf(dataSnapshot.getChildrenCount()));
                Iterable<DataSnapshot> historyEntries = dataSnapshot.getChildren();
                Log.d("DEBUGING-historyEntries", String.valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot entry : historyEntries) {
                    HistoryEntryNew historyEntry = entry.getValue(HistoryEntryNew.class);
                    // Log.d("DEBUGING-historyEntry", String.valueOf(historyEntry));
                    emptyTimes.add(historyEntry.getDate());
                }
                Log.d("DEBUGING-emptytimes", String.valueOf(emptyTimes.size()));
                if (emptyTimes.size() > 1) {
                    Prediction prediction = PredictionsHandler.generatePrediction(emptyTimes);
                    final EmptyItemPredictionEntry emptyItemPredictionEntry = new EmptyItemPredictionEntry(itemId, prediction.getTime(), prediction.getDaysNumber());

                    emptyItemsPredictionsStorageReference.orderByChild("id").equalTo(itemId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d("DEBUGING", String.valueOf(dataSnapshot.getChildrenCount()));
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
                            // emptyItemsPredictionsStorageReference.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                emptyItemsHistoryStorageReference.removeEventListener(this);
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

    public void markAsArchived(final String itemId) {
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
