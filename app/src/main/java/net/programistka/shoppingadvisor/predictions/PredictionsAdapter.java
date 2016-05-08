package net.programistka.shoppingadvisor.predictions;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.programistka.shoppingadvisor.R;
import net.programistka.shoppingadvisor.models.EmptyItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PredictionsAdapter extends RecyclerView.Adapter<PredictionsAdapter.ViewHolder> {
    private List<EmptyItem> emptyItems;
    private List<Long> selectedItems = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        private int counter = 0;
        private Boolean toggle = true;
        public View view;
        public TextView nameTextView;
        public TextView dateTextView;
        public ImageView iconView;
        public long id;

        public ViewHolder(View v) {
            super(v);
            view = v;
            v.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            initImageView(v);
                                            toggle = !toggle;
                                            initMenu();
                                        }
            });
            ShowPredictionsActivity.selectedItems = selectedItems;
        }

        private void initMenu() {
            if(counter > 0) {
                CharSequence counterLabel = Integer.toString(counter);
                ShowPredictionsActivity.menu.getItem(0).setTitle(counterLabel);
                ShowPredictionsActivity.menu.getItem(0).setVisible(true);
                ShowPredictionsActivity.menu.getItem(1).setVisible(true);
                ShowPredictionsActivity.menu.getItem(2).setVisible(true);
            }
            else {
                ShowPredictionsActivity.menu.getItem(0).setVisible(false);
                ShowPredictionsActivity.menu.getItem(1).setVisible(false);
                ShowPredictionsActivity.menu.getItem(2).setVisible(false);
            }
        }

        private void initImageView(View v) {
            ImageView imageView = (ImageView) v.findViewById(R.id.icon_imageview);
            if(toggle) {
                imageView.setImageResource(R.drawable.ic_done_black_24dp);
                selectedItems.add(id);
                counter++;
            }
            else {
                imageView.setImageResource(R.drawable.ic_event_grey_24dp);
                selectedItems.remove(id);
                counter--;
            }
        }
    }

    public PredictionsAdapter(List<EmptyItem> emptyItems) {
        this.emptyItems = emptyItems;
    }

    public void clear() {
        emptyItems.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<EmptyItem> list) {
        emptyItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public PredictionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.card_view, parent, false);
        return initViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EmptyItem emptyItem = emptyItems.get(position);
        setViewHolder(holder, emptyItem);
    }

    @Override
    public int getItemCount() {
        return emptyItems.size();
    }

    @NonNull
    private ViewHolder initViewHolder(View v) {
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.nameTextView = (TextView)v.findViewById(R.id.name_textview);
        viewHolder.dateTextView = (TextView)v.findViewById(R.id.date_textview);
        viewHolder.iconView = (ImageView)v.findViewById(R.id.icon_imageview);
        return viewHolder;
    }

    private void setViewHolder(ViewHolder holder, EmptyItem currentEmptyItem) {
        holder.nameTextView.setText(currentEmptyItem.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
        if(currentEmptyItem.getPredictionDate() < Calendar.getInstance().getTimeInMillis()){
            holder.nameTextView.setTextColor(Color.RED);
            holder.dateTextView.setTextColor(Color.RED);
        }
        holder.dateTextView.setText(sdf.format(currentEmptyItem.getPredictionDate()));
        holder.id = currentEmptyItem.getId();
        holder.iconView.setImageResource(R.drawable.ic_event_grey_24dp);
    }
}
