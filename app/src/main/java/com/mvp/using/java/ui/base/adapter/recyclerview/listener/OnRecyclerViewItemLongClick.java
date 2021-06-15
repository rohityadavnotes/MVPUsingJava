package com.mvp.using.java.ui.base.adapter.recyclerview.listener;

import android.view.View;

public interface OnRecyclerViewItemLongClick<T> {
    void OnItemLongClick(View itemView, T t, int position);
}
