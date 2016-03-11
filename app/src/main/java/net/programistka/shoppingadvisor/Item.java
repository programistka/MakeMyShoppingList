package net.programistka.shoppingadvisor;

import java.util.Date;

public class Item {
    private int _id;
    private String _name;
    private Date _creationDate;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public Date getCreationDate() {
        return _creationDate;
    }

    public void setDate(Date creationDate) {
        this._creationDate = creationDate;
    }
}
