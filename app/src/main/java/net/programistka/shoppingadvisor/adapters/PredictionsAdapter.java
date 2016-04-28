package net.programistka.shoppingadvisor.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.acitivities.ShowPredictions;
import net.programistka.shoppingadvisor.models.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PredictionsAdapter extends RecyclerView.Adapter<PredictionsAdapter.ViewHolder> {
    private List<Item> items;
    public List<Long> selectedItems = new ArrayList<>();
    private int counter = 0;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView nameTextView;
        public TextView dateTextView;
        public long id;
        private Boolean toggle = true;

        public ViewHolder(View v) {
            super(v);
            view = v;
            ImageView imageView = (ImageView) v.findViewById(R.id.icon_imageview);
            imageView.setOnClickListener(new ImageView.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ImageView imageView = (ImageView) v.findViewById(R.id.icon_imageview);
                                            if(toggle) {
                                                imageView.setImageResource(R.drawable.apply);
                                                selectedItems.add(id);
                                                counter++;
                                            }
                                            else {
                                                imageView.setImageResource(R.drawable.calendar);
                                                selectedItems.remove(id);
                                                counter--;
                                            }
                                            toggle = !toggle;
                                            if(counter > 0) {
                                                CharSequence counterLabel = Integer.toString(counter);
                                                ShowPredictions.menu.getItem(0).setTitle(counterLabel);
                                                ShowPredictions.menu.getItem(0).setVisible(true);
                                                ShowPredictions.menu.getItem(1).setVisible(true);
                                                ShowPredictions.menu.getItem(2).setVisible(true);
                                            }
                                            else {
                                                ShowPredictions.menu.getItem(0).setVisible(false);
                                                ShowPredictions.menu.getItem(1).setVisible(false);
                                                ShowPredictions.menu.getItem(2).setVisible(false);
                                            }
                                        }
            });
            ShowPredictions.selectedItems = selectedItems;
        }
    }

    public PredictionsAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public PredictionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if(currentItem.getPredictionDate().compareTo(new Date()) < 0){
            holder.nameTextView.setTextColor(Color.RED);
            holder.dateTextView.setTextColor(Color.RED);
        }
        holder.dateTextView.setText(sdf.format(currentItem.getPredictionDate()));
        holder.id = currentItem.getId();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
