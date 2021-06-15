package com.badasoftware.library.utilities;

import android.content.Context;

public class ResIdUtils {

    private ResIdUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * Get id
     *
     * @param resName resource name
     * @return resource id
     */
    public static int id(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "id", context.getPackageName());
    }

    /**
     * Get anim type resource id
     *
     * @param resName resource name
     * @return resource id
     */
    public static int anim(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "anim", context.getPackageName());
    }

    /**
     * Get layout type resource id
     *
     * @param resName resource name
     * @return resource id
     */
    public static int layout(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "layout", context.getPackageName());
    }

    /**
     * Get drawable type resource id
     *
     * @param resName resource name
     * @return resource id
     */
    public static int drawable(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
    }

    /**
     * Get string type resource id
     *
     * @param resName resource name
     * @return resource id
     */
    public static int string(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "string", context.getPackageName());
    }

    /**
     * Get raw resource id
     *
     * @param resName resource name
     * @return resource id
     */
    public static int raw(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "raw", context.getPackageName());
    }

    /**
     * Get style resource id
     *
     * @param resName resource name
     * @return resource id
     */
    public static int style(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "style", context.getPackageName());
    }
}