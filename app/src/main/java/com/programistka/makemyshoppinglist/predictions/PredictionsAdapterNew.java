package com.programistka.makemyshoppinglist.predictions;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.programistka.makemyshoppinglist.R;
import com.programistka.makemyshoppinglist.models.EmptyItem;
import com.programistka.makemyshoppinglist.models.EmptyItemNew;
import com.programistka.makemyshoppinglist.models.Prediction;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PredictionsAdapterNew extends ArrayAdapter<EmptyItemNew> {
    public PredictionsAdapterNew(@NonNull Context context, int resource, List<EmptyItemNew> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.card_view, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
        EmptyItemNew emptyItemNew = getItem(position);
        nameTextView.setText(emptyItemNew.getItemName());

        return convertView;
    }

    //    private List<EmptyItem> emptyItems;
//    private List<Long> selectedItems = new ArrayList<>();
//    private int counter = 0;
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private Boolean toggle = true;
//        public View view;
//        public TextView nameTextView;
//        public TextView dateTextView;
//        public ImageView iconView;
//        public long id;
//
//        public ViewHolder(View v) {
//            super(v);
//            view = v;
//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    initImageView(v);
//                    toggle = !toggle;
//                    initMenu();
//                }
//            });
//        }
//
//        private void initMenu() {
//            if (counter > 0) {
//                CharSequence counterLabel = Integer.toString(counter);
//                setupMenu(counterLabel);
//            } else {
//                setupMenu();
//            }
//        }
//
//        private void initIcons(boolean visible) {
//            ShowPredictionsActivity.menu.getItem(0).setVisible(visible);
//            ShowPredictionsActivity.menu.getItem(1).setVisible(visible);
//            ShowPredictionsActivity.menu.getItem(2).setVisible(visible);
//            ShowPredictionsActivity.menu.getItem(3).setVisible(visible);
//        }
//
//        private void setupMenu(CharSequence counterLabel) {
//            ShowPredictionsActivity.menu.getItem(0).setTitle(counterLabel);
//            initIcons(true);
//        }
//
//        private void setupMenu() {
//            initIcons(false);
//        }
//
//        private void initImageView(View v) {
//            ImageView imageView = (ImageView) v.findViewById(R.id.icon_imageview);
//            if (toggle) {
//                imageView.setImageResource(R.drawable.ic_done_black_24dp);
//                selectedItems.add(id);
//                ShowPredictionsActivity.selectedItems = selectedItems;
//                counter++;
//            } else {
//                imageView.setImageResource(R.drawable.ic_event_grey_24dp);
//                selectedItems.remove(id);
//                ShowPredictionsActivity.selectedItems = selectedItems;
//                counter--;
//            }
//        }
//    }
//
//    public PredictionsAdapterNew(List<EmptyItem> emptyItems) {
//        this.emptyItems = emptyItems;
//    }
//
//    @Override
//    public PredictionsAdapterNew.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(
//                parent.getContext()).inflate(
//                R.layout.card_view, parent, false);
//        return initViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        EmptyItem emptyItem = emptyItems.get(position);
//        setViewHolder(holder, emptyItem);
//    }
//
//    @Override
//    public int getItemCount() {
//        return emptyItems.size();
//    }
//
//    @NonNull
//    private ViewHolder initViewHolder(View v) {
//        ViewHolder viewHolder = new ViewHolder(v);
//        viewHolder.nameTextView = (TextView) v.findViewById(R.id.name_textview);
//        viewHolder.dateTextView = (TextView) v.findViewById(R.id.date_textview);
//        viewHolder.iconView = (ImageView) v.findViewById(R.id.icon_imageview);
//        return viewHolder;
//    }
//
//    private void setViewHolder(ViewHolder holder, EmptyItem currentEmptyItem) {
//        holder.nameTextView.setText(currentEmptyItem.getName());
//        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
//        if (currentEmptyItem.getPredictionDate() < Calendar.getInstance().getTimeInMillis()) {
//            holder.dateTextView.setTextColor(Color.RED);
//        }
//        holder.dateTextView.setText(df.format(currentEmptyItem.getPredictionDate()));
//        holder.id = currentEmptyItem.getId();
//        holder.iconView.setImageResource(R.drawable.ic_event_grey_24dp);
//    }
}
