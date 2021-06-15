package com.mvp.using.java.ui.base.adapter.recyclerview.listener;

import android.view.View;

public interface OnRecyclerViewItemChildClick<T> {
    void OnItemChildClick(View viewChild, T t, int position);
}
