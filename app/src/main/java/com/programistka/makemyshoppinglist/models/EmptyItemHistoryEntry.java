package com.programistka.makemyshoppinglist.models;

import java.util.Date;

public class EmptyItemHistoryEntry {

    private String id;
    private long date;

    public EmptyItemHistoryEntry() {}

    public EmptyItemHistoryEntry(String id, long date) {
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
