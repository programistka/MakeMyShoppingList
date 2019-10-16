package com.programistka.makemyshoppinglist;

import com.programistka.makemyshoppinglist.addemptyitem.AddEmptyItemView;
import com.programistka.makemyshoppinglist.dbhandlers.FirebaseDbHandler;

import org.junit.Test;

import java.util.Calendar;

import static org.mockito.Mockito.mock;

public class FirebaseDbHandlersTest  {
    FirebaseDbHandler firebaseDbHandler = new FirebaseDbHandler();

    @Test
    public void testWhenAddedEmptyItemForTheFirstTimeThenVisibleInItems() {
        AddEmptyItemView view = mock(AddEmptyItemView.class);
        Calendar calendar = mock(Calendar.class);
        firebaseDbHandler.addEmptyItem(view, "testItem", calendar.getTimeInMillis());
    }
}
