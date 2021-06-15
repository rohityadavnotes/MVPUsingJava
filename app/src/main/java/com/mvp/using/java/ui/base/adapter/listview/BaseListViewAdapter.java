package com.mvp.using.java.ui.base.adapter.listview;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseListViewAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> list;

    public BaseListViewAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return inflateView(position, convertView, parent);
    }

    public abstract View inflateView(int position, View convertView, ViewGroup parent);
}
