package com.mvp.using.java.ui.search;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mvp.using.java.R;
import java.util.ArrayList;
import java.util.List;

public class SearchAutoCompleteAdapter extends ArrayAdapter<Item> {

    private LayoutInflater layoutInflater;
    private List<Item> allItemList;
    private List<Item> filteredItemList;

    public SearchAutoCompleteAdapter(@NonNull Context context, @NonNull List<Item> items) {
        super(context, 0, items);
        layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        allItemList = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemViewHolder itemViewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.autocomplete_row, parent, false);
            itemViewHolder = new ItemViewHolder(convertView);
            convertView.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }

        Item row = (Item) getItem(position);
        ((ItemViewHolder) itemViewHolder).setData(row);
        return convertView;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            filteredItemList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredItemList.addAll(allItemList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Item place : allItemList) {
                    if (place.getString().toLowerCase().contains(filterPattern)) {
                        filteredItemList.add(place);
                    }
                }
            }

            results.values = filteredItemList;
            results.count = filteredItemList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Item) resultValue).getString();
        }
    };

    private static class ItemViewHolder {
        private TextView textView;

        public ItemViewHolder(View item) {
            textView = (TextView) item.findViewById(R.id.textView);
        }

        public void setData(Item itemPosition) {
            textView.setText(itemPosition.getString());
        }
    }
}
