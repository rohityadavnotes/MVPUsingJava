package com.mvp.using.java.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.badasoftware.library.utilities.fragment.FragmentTransactionAnimations;
import com.badasoftware.library.utilities.fragment.ManageFragment;
import com.google.android.material.button.MaterialButton;
import com.mvp.using.java.R;
import com.mvp.using.java.ui.base.BaseActivity;
import com.mvp.using.java.ui.dashboard.fragment.FragmentConstants;
import com.mvp.using.java.ui.dashboard.fragment.aboutus.AboutUsFragment;
import com.mvp.using.java.ui.dashboard.fragment.profile.ProfileFragment;

public class FragmentTestActivity extends BaseActivity {

    public static final String TAG = FragmentTestActivity.class.getSimpleName();

    private MaterialButton addFragmentMaterialButton, replaceFragmentMaterialButton;
    private FragmentManager fragmentManager;
    private ManageFragment manageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_fragment_test;
    }

    @Override
    protected void initializeView() {
        addFragmentMaterialButton       = findView(R.id.addFragmentMaterialButton);
        replaceFragmentMaterialButton   = findView(R.id.replaceFragmentMaterialButton);
    }

    @Override
    protected void initializeObject() {
        fragmentManager = getSupportFragmentManager();
        manageFragment  = new ManageFragment(fragmentManager, R.id.fragmentContainer);
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
    protected void setOnClickListener() {
        setOnClick(addFragmentMaterialButton);
        setOnClick(replaceFragmentMaterialButton);
    }

    @Override
    protected void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.addFragmentMaterialButton:
                Bundle profileBundle = new Bundle();
                profileBundle.putString(FragmentConstants.TOOLBAR_HEADING, getString(R.string.profile));

                ProfileFragment profileFragment = ProfileFragment.newInstance(profileBundle);
                FragmentTransactionAnimations profileFragmentTransactionAnimations = new FragmentTransactionAnimations();
                manageFragment.addFragment(profileFragment, profileFragmentTransactionAnimations, true);
                break;
            case R.id.replaceFragmentMaterialButton:
                Bundle aboutAsBundle = new Bundle();
                aboutAsBundle.putString(FragmentConstants.TOOLBAR_HEADING, getString(R.string.profile));

                AboutUsFragment aboutAsFragment = AboutUsFragment.newInstance(aboutAsBundle);
                manageFragment.replaceFragment(
                        aboutAsFragment,
                        null,
                        true,
                        null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        int fragments = fragmentManager.getBackStackEntryCount();
        if(fragments > 1)
        {
            fragmentManager.popBackStack();
            Log.e(TAG, "CURRENT FRAGMENT ENTRY COUNT : "+fragments);
        }
        /*
         * If already set root fragment then show exit alert dialog
         */
        else
        {
            Log.e(TAG, "SHOW EXIT ALERT DIALOG");
        }
    }
}