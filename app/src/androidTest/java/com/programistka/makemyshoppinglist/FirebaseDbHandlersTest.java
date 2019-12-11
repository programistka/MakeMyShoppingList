package com.programistka.makemyshoppinglist;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.programistka.makemyshoppinglist.addemptyitem.AddEmptyItemView;
import com.programistka.makemyshoppinglist.dbhandlers.DatabaseConfig;
import com.programistka.makemyshoppinglist.dbhandlers.DatabaseConfigTest;
import com.programistka.makemyshoppinglist.dbhandlers.FirebaseDbHandler;
import com.programistka.makemyshoppinglist.models.EmptyItemNew;
import com.programistka.makemyshoppinglist.models.EmptyItemPredictionEntry;
import com.programistka.makemyshoppinglist.models.HistoryEntryNew;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class FirebaseDbHandlersTest  {
    DatabaseConfig databaseConfig = new DatabaseConfigTest();
    FirebaseDbHandler firebaseDbHandler = FirebaseDbHandler.init(databaseConfig);

    @Test
    public void testWhenAddedEmptyItemForTheFirstTimeThenNewItemEntryShouldBeAddedAndNewHistoryEntryShouldBeAdded() throws Exception {
        databaseConfig.getItems().setValue(null);
        databaseConfig.getHistory().setValue(null);
        databaseConfig.getPredictions().setValue(null);
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        //22.10.2019 10:00 - TODO: should midnight be added?
        final long time = 1571731200000L;
        final String itemName = "item1";
        firebaseDbHandler.addEmptyItem2(view, itemName, time);

        databaseConfig.getItems().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                EmptyItemNew emptyItemNew = dataSnapshot.getValue(EmptyItemNew.class);
                Assert.assertEquals(itemName, emptyItemNew.getItemName());
                databaseConfig.getItems().removeEventListener(this);
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
                databaseConfig.getHistory().removeEventListener(this);
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
        databaseConfig.getItems().push();
        databaseConfig.getHistory().push();
        databaseConfig.getPredictions().push();
    }

    @Test
    public void testWhenAddedEmptyItemNotForTheSecondTimeThenNewHistoryEntryShouldBeAddedAndTheNewPredictionEntryShouldBeAdded() throws Exception {
        databaseConfig.getItems().setValue(null);
        databaseConfig.getHistory().setValue(null);
        databaseConfig.getPredictions().setValue(null);
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        final long time1 = 1570665600000L; // 10.10.2019 00:00:00 UTC
        final String itemName = "item1";
        String key = firebaseDbHandler.addEmptyItem2(view, itemName, time1);

        Thread.sleep(4000);

        final long time2 = 1571702400000L; // 22.10.2019 00:00:00 UTC
        firebaseDbHandler.addEmptyExistingItem(key, time2);

        databaseConfig.getHistory().orderByChild("id").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long numberOfHistoryItems = dataSnapshot.getChildrenCount();
                Assert.assertEquals(2, numberOfHistoryItems);
                databaseConfig.getHistory().removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Thread.sleep(4000);

        databaseConfig.getPredictions().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                EmptyItemPredictionEntry emptyItemPredictionEntry = dataSnapshot.getValue(EmptyItemPredictionEntry.class);
                Assert.assertEquals(12, emptyItemPredictionEntry.getDaysToRunOut());
                Assert.assertEquals(1572739200000L, emptyItemPredictionEntry.getNextEmptyItemDate());
                databaseConfig.getPredictions().removeEventListener(this);
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
        databaseConfig.getItems().push();
        databaseConfig.getHistory().push();
        databaseConfig.getPredictions().push();
    }

    @Test
    public void testWhenAddedEmptyItemNotForTheThirdTimeThenNewHistoryEntryShouldBeAddedAndThePredictionEntryShouldBeUpdated() throws Exception {
        databaseConfig.getItems().setValue(null);
        databaseConfig.getHistory().setValue(null);
        databaseConfig.getPredictions().setValue(null);
        Log.d("DEBUGING", "START");

        AddEmptyItemView view = mock(AddEmptyItemView.class);
        final long time1 = 1570665600000L; // 10.10.2019 00:00:00 UTC
        final String itemName = "item1";
        String key = firebaseDbHandler.addEmptyItem2(view, itemName, time1);
        Thread.sleep(4000);
        Log.d("DEBUGING", "START2");

        final long time2 = 1571702400000L; // 22.10.2019 00:00:00 UTC
        firebaseDbHandler.addEmptyExistingItem2(key, time2);
        Thread.sleep(4000);
        Log.d("DEBUGING", "START3");

        final long time3 = 1572134400000L; // 27.10.2019 00:00:00 UTC
        firebaseDbHandler.addEmptyExistingItem2(key, time3);

        Thread.sleep(4000);

        Log.d("DEBUGING", "START4");

        databaseConfig.getItems().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("DEBUGING", "GetItems");
                Assert.assertEquals(1, dataSnapshot.getChildrenCount());
                databaseConfig.getItems().removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Thread.sleep(4000);

        databaseConfig.getHistory().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("D", "GetHistory");
                Assert.assertEquals(3, dataSnapshot.getChildrenCount());
                databaseConfig.getHistory().removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Thread.sleep(4000);

        databaseConfig.getPredictions().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("D", "GetPreds");
                Assert.assertEquals(1, dataSnapshot.getChildrenCount());
                for(DataSnapshot prediction: dataSnapshot.getChildren()) {
                    EmptyItemPredictionEntry emptyItemPredictionEntry = prediction.getValue(EmptyItemPredictionEntry.class);
                    Assert.assertEquals(9, emptyItemPredictionEntry.getDaysToRunOut());
                    Assert.assertEquals(1572912000000L, emptyItemPredictionEntry.getNextEmptyItemDate());
                }
                databaseConfig.getPredictions().removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseConfig.getItems().push();
        databaseConfig.getHistory().push();
        databaseConfig.getPredictions().push();
    }

}
