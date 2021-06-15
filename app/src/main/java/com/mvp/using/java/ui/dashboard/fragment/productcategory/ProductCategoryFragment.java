package com.mvp.using.java.ui.dashboard.fragment.productcategory;

import android.os.Bundle;
import android.view.View;

import com.badasoftware.library.utilities.fragment.IFragment;
import com.mvp.using.java.R;
import com.mvp.using.java.ui.base.BaseFragment;
import com.mvp.using.java.ui.dashboard.fragment.FragmentConstants;

/**
 * Use the {@link ProductCategoryFragment#newInstance(Bundle bundle)} factory method to
 * create an instance of this fragment.
 */
public class ProductCategoryFragment extends BaseFragment implements IFragment {

    public static final String TAG = ProductCategoryFragment.class.getSimpleName();

    private String bundleData;

    public ProductCategoryFragment() {
    }

    public static ProductCategoryFragment newInstance(Bundle bundle) {
        ProductCategoryFragment profileFragment = new ProductCategoryFragment();
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundleData = getArguments().getString(FragmentConstants.TOOLBAR_HEADING);
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_product_category;
    }

    @Override
    protected void initializeView() {
    }

    @Override
    protected void initializeObject() {
    }

    @Override
    protected void initializeToolBar() {
    }

    @Override
    protected void initializeCallbackListener() {
    }

    @Override
    protected void addTextChangedListener() {
    }

    @Override
    protected void addOnClickListener() {
    }

    @Override
    protected void handleClickEvent(View view) {
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }
}