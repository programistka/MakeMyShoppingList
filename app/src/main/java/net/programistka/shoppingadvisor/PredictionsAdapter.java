package net.programistka.shoppingadvisor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by maga on 13.03.16.
 */
public class PredictionsAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> suggestions;
    private ArrayList<Item> items;
    private ArrayList<Item> itemsAll;

    public PredictionsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);

        this.suggestions = new ArrayList<Item>();
        this.items = items;
        this.itemsAll = (ArrayList<Item>) items.clone();
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.predictions_row, parent, false);
        }
        TextView tvItemName = (TextView) convertView.findViewById(R.id.txtItemName);
        TextView tvItemPredictionDate = (TextView) convertView.findViewById(R.id.txtItemPredictionDate);
        tvItemName.setText(item.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tvItemPredictionDate.setText(dateFormat.format(item.getPredictionDate()));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Item)(resultValue)).getName();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Item customer : itemsAll) {
                    if(customer.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(customer);
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
            if(results != null && results.count > 0) {
                clear();
                for (Item c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
