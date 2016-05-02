package net.programistka.shoppingadvisor.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.models.EmptyItem;

import java.text.SimpleDateFormat;
import java.util.List;

public class EmptyItemsHistoryAdapter extends RecyclerView.Adapter<EmptyItemsHistoryAdapter.ViewHolder> {
    private List<EmptyItem> emptyItems;

    public EmptyItemsHistoryAdapter(List<EmptyItem> emptyItems) {
        this.emptyItems = emptyItems;
    }

    @Override
    public EmptyItemsHistoryAdapter.ViewHolder
        onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(
                    parent.getContext()).inflate(
                    R.layout.card_view, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            viewHolder.nameTextView = (TextView)v.findViewById(R.id.name_textview);
            viewHolder.dateTextView = (TextView)v.findViewById(R.id.date_textview);
            return viewHolder;
        }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView dateTextView;
        public ViewHolder(View v) { super(v); }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EmptyItem currentEmptyItem = emptyItems.get(position);
        holder.nameTextView.setText(currentEmptyItem.getName());
        holder.dateTextView.setText(new SimpleDateFormat("dd MM yyyy").format(currentEmptyItem.getCreationDate()));
    }

    @Override
    public int getItemCount() {
        System.out.println(this.emptyItems.size());
        return this.emptyItems.size();
    }
}
