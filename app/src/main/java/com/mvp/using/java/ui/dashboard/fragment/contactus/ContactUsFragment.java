package com.mvp.using.java.ui.dashboard.fragment.contactus;

import android.os.Bundle;
import android.view.View;
import com.badasoftware.library.utilities.fragment.IFragment;
import com.mvp.using.java.R;
import com.mvp.using.java.ui.base.BaseFragment;
import com.mvp.using.java.ui.dashboard.fragment.FragmentConstants;

/**
 * Use the {@link ContactUsFragment#newInstance(Bundle bundle)} factory method to
 * create an instance of this fragment.
 */
public class ContactUsFragment extends BaseFragment implements IFragment {

    public static final String TAG = ContactUsFragment.class.getSimpleName();

    private String bundleData;

    public ContactUsFragment() {
    }

    public static ContactUsFragment newInstance(Bundle bundle) {
        ContactUsFragment profileFragment = new ContactUsFragment();
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
        return R.layout.fragment_contact_us;
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