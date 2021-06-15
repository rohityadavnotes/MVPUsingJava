package com.mvp.using.java.ui.base.adapter.recyclerview.listener;

import android.view.View;

public interface OnRecyclerViewItemChildLongClick<T> {
    void OnItemChildLongClick(View viewChild, T t, int position);
}
