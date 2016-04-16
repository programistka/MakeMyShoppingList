package net.programistka.shoppingadvisor.adapters;

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

/**
 * Created by maga on 13.03.16.
 */
public class PredictionsAdapter extends RecyclerView.Adapter<PredictionsAdapter.ViewHolder> {
    private ArrayList<Item> items;
    public ArrayList<Long> selectedItems = new ArrayList<>();
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
            view.setOnClickListener(new View.OnClickListener() {
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
                    }
                    else {
                        ShowPredictions.menu.getItem(0).setVisible(false);
                        ShowPredictions.menu.getItem(1).setVisible(false);
                    }
                }
            });
            ShowPredictions.selectedItems = selectedItems;
        }
    }

    public PredictionsAdapter(ArrayList<Item> items) {
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
        holder.dateTextView.setText(sdf.format(currentItem.getPredictionDate()));
        holder.id = currentItem.getId();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
