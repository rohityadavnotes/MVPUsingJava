package com.mvp.using.java.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mvp.using.java.ui.base.views.BaseFragmentView;
import com.mvp.using.java.ui.dashboard.fragmentnavigator.MyAppFragment;

public abstract class BaseFragment extends MyAppFragment implements View.OnClickListener, BaseFragmentView {

    private static final String TAG = BaseFragment.class.getSimpleName();

    /*
     ***********************************************************************************************
     ******************************************* Properties ****************************************
     ***********************************************************************************************
     */
    protected BaseActivity baseActivity;
    protected View rootView;
    public SparseArray<View> viewSparseArray;

    /*
     ***********************************************************************************************
     ********************************* BaseFragment abstract methods *******************************
     ***********************************************************************************************
     */
    @LayoutRes
    protected abstract int getLayoutID();

    protected abstract void initializeView();

    protected abstract void initializeObject();

    protected abstract void initializeToolBar();

    protected abstract void initializeCallbackListener();

    protected abstract void addTextChangedListener();

    protected abstract void addOnClickListener();

    protected abstract void handleClickEvent(View view);

    /*
     ***********************************************************************************************
     ********************************* Fragment lifecycle methods **********************************
     ***********************************************************************************************
     */

    /*
     * OnAttach(Context context) is not call, If API level of the android version is lower than 23.
     * Because OnAttach(Context context) is added in API level 23.
     */
    @TargetApi(23)
    @Override
    @CallSuper
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach(@NonNull Context context)");

        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.baseActivity = activity;
            activity.onFragmentAttached();
        }
    }

    /*
     * OnAttach(Activity activity) is not call, If API level of the android version is greater than 22.
     * Because OnAttach(Activity activity) is deprecated in API level 23, but must remain to allow compatibility with api<23
     */
    @Override
    @CallSuper
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach(@NonNull Activity activity)");

        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            this.baseActivity = baseActivity;
            baseActivity.onFragmentAttached();
        }
    }

    @Override
    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle savedInstanceState)");
    }

    @Nullable
    @Override
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)");
        rootView = inflater.inflate(getLayoutID(), container, false);
        return rootView;
    }

    @Override
    @CallSuper
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated(@NonNull View view, Bundle savedInstanceState)");

        viewSparseArray = new SparseArray<>();

        initializeView();
        initializeObject();
        initializeToolBar();
        initializeCallbackListener();
        addTextChangedListener();
        addOnClickListener();
    }

    @Override
    @CallSuper
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated(@Nullable Bundle savedInstanceState)");
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    @CallSuper
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    @CallSuper
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView()");
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    @CallSuper
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
        baseActivity = null;
    }

    /**
     * Find the view control and put it in the collection
     *
     * @param viewID the id of the view to be found
     * @param <E>    the returned view
     * @return
     */
    public <E extends View> E findView(int viewID) {
        E view = (E) viewSparseArray.get(viewID);
        if (view == null) {
            /* If the searched view is not in the collection, add it to the collection */
            view = (E) rootView.findViewById(viewID);
            if (view != null) {
                viewSparseArray.append(viewID, view);
            }
        }
        return view;
    }

    /**
     * View's click event
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        handleClickEvent(v);
    }

    /**
     * Set the click event to the view
     *
     * @param view The view bound to the event
     * @param <E>
     */
    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }

    /*
     ***********************************************************************************************
     ******************************** Fragment context and activity methods ************************
     ***********************************************************************************************
     */
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    public Activity getActivityContext() {
        return getActivity();
    }

    public void goBack() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    /**
     * Get a layout inflater instance.
     *
     * @return LayoutInflater
     */
    public LayoutInflater getInflater() {
        return getBaseActivity().getLayoutInflater();
    }

    /**
     * Get the activity of the current fragment
     *
     * @return SupportActivity
     */
    public BaseActivity getBaseActivity() {
        return (BaseActivity) super.getActivity();
    }

    public BaseActivity getBaseActivitySecond() {
        return baseActivity;
    }

    /*
     ***********************************************************************************************
     ************************************** Accessors methods **************************************
     ***********************************************************************************************
     */
    @Override
    public void showProgressDialog() {
        getBaseActivity().showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        getBaseActivity().hideProgressDialog();
    }

    @Override
    public void showDialogMessage(String dialogTitle, String dialogMessage) {
        getBaseActivity().showDialogMessage(dialogTitle, dialogMessage);
    }

    @Override
    public void showDialogMessage(int dialogTitleResId, int dialogMessageResId) {
        getBaseActivity().showDialogMessage(dialogTitleResId, dialogMessageResId);
    }

    @Override
    public void showToast(String message) {
        getBaseActivity().showToast(message);
    }

    @Override
    public void showToast(int messageResId) {
        getBaseActivity().showToast(messageResId);
    }

    /*
     ***********************************************************************************************
     **************************** Fragment Attach Detach CallBack Methods **************************
     ***********************************************************************************************
     */
    public interface Callback {
        void onFragmentAttached();
        void onFragmentDetached(String tag);
    }
}
