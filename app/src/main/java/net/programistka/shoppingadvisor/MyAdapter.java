package net.programistka.shoppingadvisor;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by maga on 13.03.16.
 */
public class MyAdapter<Item> extends ArrayAdapter<Item> {

    private final List<Item> values;

    public MyAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        this.values = objects;
    }

    @Override
    public Item getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        Item i = getItem(position);
        return 0;
    }
}
