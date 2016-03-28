package net.programistka.shoppingadvisor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.programistka.shoppingadvisor.models.Item;
import net.programistka.shoppingadvisor.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by maga on 13.03.16.
 */
public class PredictionsAdapter extends RecyclerView.Adapter<PredictionsAdapter.ViewHolder> {
    private ArrayList<Item> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView dateTextView;
        public ViewHolder(View v) { super(v); }
    }

    public PredictionsAdapter(ArrayList<Item> items) {
        this.items = items;
    }
    @Override
    public PredictionsAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.nameTextView = (TextView)v.findViewById(R.id.name_textview);
        viewHolder.dateTextView = (TextView)v.findViewById(R.id.date_textview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.nameTextView.setText(currentItem.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
        holder.dateTextView.setText(sdf.format(currentItem.getPredictionDate()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
