package com.badasoftware.library.utilities.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import java.util.Stack;

public class ActivityStackManager {

    private static Stack<Activity> activityStack;
    private static volatile ActivityStackManager instance = null;

    private ActivityStackManager() {
    }

    public static ActivityStackManager getActivityStackManager() {
        if (instance == null) {
            synchronized (ActivityStackManager.class) {
                if (instance == null) {
                    instance = new ActivityStackManager();
                }
            }
        }
        return instance;
    }

    /* Add activity to Stack */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /* Get the first activity in the stack. */
    public Activity getFirstActivity() {
        if (activityStack == null || activityStack.isEmpty()) return null;
        Activity activity = activityStack.firstElement();
        return activity;
    }

    /* Get the current Activity */
    public Activity getCurrentActivity() {
        if (activityStack == null || activityStack.isEmpty()) return null;
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * Get the previous Activity of the current Activity
     * (perform this operation after adding current activity using popActivity(Activity activity) method)
     */
    public Activity preActivity() {
        int index = activityStack.size() - 2;
        if (index < 0) {
            return null;
        }
        Activity activity = activityStack.get(index);
        return activity;
    }

    /* Remove the current Activity (the last one pushed in the stack) */
    public void finishCurrentActivity() {
        Activity activity = activityStack.lastElement();
        popActivity(activity);
    }

    /**
     * Finish the activity and remove it from stack.
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activityStack == null) return;
        if (activity != null) {
            activity.finish();
            activity.overridePendingTransition(0, 0);
            activityStack.remove(activity);
            activity = null;
        }
    }

    /* remove all activity. */
    public void popAllActivity() {
        if (activityStack == null) return;
        while (true) {
            if (activityStack.empty()) {
                break;
            }
            Activity activity = getCurrentActivity();
            popActivity(activity);
        }
    }

    public void popAllActivityWithOutCurrent() {
        Activity activity = getCurrentActivity();
        while (true) {
            if (activityStack.size() == 1) {
                break;
            }
            if (getFirstActivity() == activity) {
                break;
            } else {
                popActivity(getFirstActivity());
            }
        }
    }

    /**
     * Return to the specified activity
     *
     * @param cls
     */
    public void returnToActivity(Class<?> cls) {
        while (activityStack.size() != 0)
            if (activityStack.peek().getClass() == cls) {
                break;
            } else {
                popActivity(activityStack.peek());
            }
    }

    /**
     * Whether the specified activity has been opened
     *
     * @param cls
     * @return
     */
    public boolean isOpenActivity(Class<?> cls) {
        if (activityStack != null) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (cls == activityStack.peek().getClass()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Exit the application
     *
     * @param context      context
     * @param alsoBackgroundProcess also stop background operation
     */
    public void AppExit(Context context, Boolean alsoBackgroundProcess) {
        try {
            popAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            /* Note, if you have a background program running, please do not support this sentence */
            if (alsoBackgroundProcess) {
                System.exit(0);
            }
        }
    }
}
