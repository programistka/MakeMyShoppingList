package net.programistka.shoppingadvisor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import net.programistka.shoppingadvisor.models.Item;
import net.programistka.shoppingadvisor.R;

import java.util.ArrayList;
import java.util.List;

public class SuggestionsAdapter extends ArrayAdapter<Item> {
    private List<Item> items = new ArrayList<>();
    private List<Item> itemsAll = new ArrayList<>();

    public SuggestionsAdapter(Context context, List<Item> items) {
        super(context, 0, items);

        this.items = items;
        this.itemsAll = new ArrayList<>(items);
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.suggestion_row, parent, false);
        }
        TextView tvItemName = (TextView) convertView.findViewById(R.id.txtItemName);
        tvItemName.setText(item.getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    private Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Item)(resultValue)).getName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                List<Item> suggestions = new ArrayList<>();
                for (Item item : itemsAll) {
                    if(item.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(item);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Item> filteredList = (ArrayList<Item>) results.values;
            if(results.count > 0) {
                clear();
                for (Item c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
