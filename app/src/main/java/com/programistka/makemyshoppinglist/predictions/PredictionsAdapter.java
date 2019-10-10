package com.programistka.makemyshoppinglist.predictions;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.models.NewItem;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PredictionsAdapter extends RecyclerView.Adapter<PredictionsAdapter.ViewHolder> {
    private List<NewItem> emptyItems;
    private List<String> selectedItems = new ArrayList<>();
    private int counter = 0;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Boolean toggle = true;
        public View view;
        public TextView nameTextView;
        public TextView dateTextView;
        public ImageView iconView;
        public String id;

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
        }

        private void initMenu() {
            if (counter > 0) {
                CharSequence counterLabel = Integer.toString(counter);
                setupMenu(counterLabel);
            } else {
                setupMenu();
            }
        }

        private void initIcons(boolean visible) {
            ShowPredictionsActivity.menu.getItem(0).setVisible(visible);
            ShowPredictionsActivity.menu.getItem(1).setVisible(visible);
            ShowPredictionsActivity.menu.getItem(2).setVisible(visible);
            ShowPredictionsActivity.menu.getItem(3).setVisible(visible);
        }

        private void setupMenu(CharSequence counterLabel) {
            ShowPredictionsActivity.menu.getItem(0).setTitle(counterLabel);
            initIcons(true);
        }

        private void setupMenu() {
            initIcons(false);
        }

        private void initImageView(View v) {
            ImageView imageView = v.findViewById(R.id.icon_imageview);
            if (toggle) {
                imageView.setImageResource(R.drawable.ic_done_black_24dp);
                selectedItems.add(id);
                ShowPredictionsActivity.selectedItems = selectedItems;
                counter++;
            } else {
                imageView.setImageResource(R.drawable.ic_event_grey_24dp);
                selectedItems.remove(id);
                ShowPredictionsActivity.selectedItems = selectedItems;
                counter--;
            }
        }
    }

    public PredictionsAdapter(List<NewItem> emptyItems) {
        this.emptyItems = emptyItems;
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
        NewItem emptyItem = emptyItems.get(position);
        setViewHolder(holder, emptyItem);
    }

    @Override
    public int getItemCount() {
        return emptyItems.size();
    }

    @NonNull
    private ViewHolder initViewHolder(View v) {
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.nameTextView = v.findViewById(R.id.name_textview);
        viewHolder.dateTextView = v.findViewById(R.id.date_textview);
        viewHolder.iconView = v.findViewById(R.id.icon_imageview);
        return viewHolder;
    }

    private void setViewHolder(ViewHolder holder, NewItem currentEmptyItem) {
        holder.nameTextView.setText(currentEmptyItem.getName());
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        if (currentEmptyItem.getNextEmptyItemDate() < Calendar.getInstance().getTimeInMillis()) {
            holder.dateTextView.setTextColor(Color.RED);
        }
        holder.dateTextView.setText(df.format(currentEmptyItem.getNextEmptyItemDate()));
        holder.id = currentEmptyItem.getId();
        holder.iconView.setImageResource(R.drawable.ic_event_grey_24dp);
    }
}
