package com.programistka.makemyshoppinglist.addemptyitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.R;

import java.util.ArrayList;
import java.util.List;

public class SuggestionsAdapter extends ArrayAdapter<EmptyItem> {
    //TODO: make it work again
    private List<EmptyItem> emptyItems = new ArrayList<>();
    private List<EmptyItem> itemsAll = new ArrayList<>();

    public SuggestionsAdapter(Context context, List<EmptyItem> emptyItems) {
        super(context, 0, emptyItems);

        this.emptyItems = emptyItems;
        this.itemsAll = new ArrayList<>(emptyItems);
    }

    @Override
    public EmptyItem getItem(int position) {
        return emptyItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EmptyItem emptyItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.suggestion_row, parent, false);
        }
        TextView tvItemName = convertView.findViewById(R.id.emptyItemName);
        tvItemName.setText(emptyItem.getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    private Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((EmptyItem) (resultValue)).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                List<EmptyItem> suggestions = new ArrayList<>();
                for (EmptyItem emptyItem : itemsAll) {
                    if (emptyItem.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(emptyItem);
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
            ArrayList<EmptyItem> filteredList = (ArrayList<EmptyItem>) results.values;
            if (results.count > 0) {
                clear();
                for (EmptyItem c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
