package com.programistka.makemyshoppinglist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.programistka.makemyshoppinglist.addemptyitem.AddEmptyItemView;
import com.programistka.makemyshoppinglist.dbhandlers.DatabaseConfig;
import com.programistka.makemyshoppinglist.dbhandlers.DatabaseConfigTest;
import com.programistka.makemyshoppinglist.dbhandlers.FirebaseDbHandler;
import com.programistka.makemyshoppinglist.models.EmptyItemNew;
import com.programistka.makemyshoppinglist.models.EmptyItemPredictionEntry;
import com.programistka.makemyshoppinglist.models.HistoryEntryNew;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class FirebaseDbHandlersTest  {
    DatabaseConfig databaseConfig = new DatabaseConfigTest();
    FirebaseDbHandler firebaseDbHandler = new FirebaseDbHandler(databaseConfig);

    @Before
    public void init() {
        databaseConfig.getItems().setValue(null);
        databaseConfig.getHistory().setValue(null);
        databaseConfig.getPredictions().setValue(null);
    }

    @After
    public void teardown() {
//        databaseConfig.getItems().push();
//        databaseConfig.getHistory().push();
//        databaseConfig.getPredictions().push();
    }

    @Test
    public void testWhenAddedEmptyItemForTheFirstTimeThenNewItemEntryShouldBeAddedAndNewHistoryEntryShouldBeAdded() {
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        //22.10.2019 10:00 - TODO: should midnight be added?
        final long time = 1571731200000L;
        final String itemName = "item1";
        firebaseDbHandler.addEmptyItem(view, itemName, time);

        databaseConfig.getItems().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                EmptyItemNew emptyItemNew = dataSnapshot.getValue(EmptyItemNew.class);
                Assert.assertEquals(itemName, emptyItemNew.getItemName());
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

        databaseConfig.getHistory().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                HistoryEntryNew historyEntryNew = dataSnapshot.getValue(HistoryEntryNew.class);
                Assert.assertEquals(time, historyEntryNew.getDate());
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

    @Test
    public void testWhenAddedEmptyItemNotForTheSecondTimeThenNewHistoryEntryShouldBeAddedAndTheNewPredictionEntryShouldBeAdded() {
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        //22.10.2019 - TODO: should midnight be added?
        final long time1 = 1570665600000L; // 10.10.2019 00:00:00 UTC
        final String itemName = "item1";
        firebaseDbHandler.addEmptyItem(view, itemName, time1);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        databaseConfig.getItems().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final long time2 = 1571702400000L; // 22.10.2019 00:00:00 UTC
                firebaseDbHandler.addEmptyExistingItem(dataSnapshot.getKey(), time2);
                databaseConfig.getHistory().orderByChild("id").equalTo(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long numberOfHistoryItems = dataSnapshot.getChildrenCount();
                        Assert.assertEquals(2, numberOfHistoryItems);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        databaseConfig.getPredictions().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                EmptyItemPredictionEntry emptyItemPredictionEntry = dataSnapshot.getValue(EmptyItemPredictionEntry.class);
                Assert.assertEquals(12, emptyItemPredictionEntry.getDaysToRunOut());
                Assert.assertEquals(1572739200000L, emptyItemPredictionEntry.getNextEmptyItemDate());
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

    @Test
    public void testWhenAddedEmptyItemNotForTheThirdTimeThenNewHistoryEntryShouldBeAddedAndThePredictionEntryShouldBeUpdated() {
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        //22.10.2019 - TODO: should midnight be added?
        final long time1 = 1570665600000L; // 10.10.2019 00:00:00 UTC
        final String itemName = "item1";
        firebaseDbHandler.addEmptyItem(view, itemName, time1);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        databaseConfig.getItems().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final long time2 = 1571702400000L; // 22.10.2019 00:00:00 UTC
                firebaseDbHandler.addEmptyExistingItem(dataSnapshot.getKey(), time2);

                final long time3 = 1571702400000L; // 22.10.2019 00:00:00 UTC
                firebaseDbHandler.addEmptyExistingItem(dataSnapshot.getKey(), time3);

                databaseConfig.getHistory().orderByChild("id").equalTo(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long numberOfHistoryItems = dataSnapshot.getChildrenCount();
                        Assert.assertEquals(3, numberOfHistoryItems);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        databaseConfig.getPredictions().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                EmptyItemPredictionEntry emptyItemPredictionEntry = dataSnapshot.getValue(EmptyItemPredictionEntry.class);
                Assert.assertEquals(12, emptyItemPredictionEntry.getDaysToRunOut());
                Assert.assertEquals(1572739200000L, emptyItemPredictionEntry.getNextEmptyItemDate());
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

}
