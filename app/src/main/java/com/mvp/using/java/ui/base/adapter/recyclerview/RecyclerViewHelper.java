package com.mvp.using.java.ui.base.adapter.recyclerview;

import android.content.Context;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by long on 2016/3/30.
 */
public class RecyclerViewHelper {
    /**
     * 配置RecyclerView
     * @param view
     */
    public static void init(Context context, RecyclerView view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setHasFixedSize(true);
        view.setLayoutManager(layoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 配置RecyclerView
     * @param view
     */
    public static void initWithDecoration(Context context, RecyclerView view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setHasFixedSize(true);
        view.setLayoutManager(layoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        view.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
    }
}
