package com.badasoftware.library.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class UIUtils {

    private UIUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    @SuppressWarnings("deprecation")
    public static Drawable getDrawable(Context context, int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(resourceId, null);
        }
        return context.getResources().getDrawable(resourceId);
    }

    @SuppressWarnings("deprecation")
    public static int getColor(@NonNull Context context, int resourceId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return context.getResources().getColor(resourceId);
        } else {
            return context.getResources().getColor(resourceId, null);
        }
    }

    public static void setButtonBackgroundColor(Context context, Button button, int color) {
        if (Build.VERSION.SDK_INT >= 23) {
            button.setBackgroundColor(context.getResources().getColor(color, null));
        } else {
            button.setBackgroundColor(context.getResources().getColor(color));
        }
    }

    public static void setTextViewBackgroundColor(Context context, TextView textView, int color) {
        if (Build.VERSION.SDK_INT >= 23) {
            textView.setBackgroundColor(context.getResources().getColor(color, null));
        } else {
            textView.setBackgroundColor(context.getResources().getColor(color));
        }
    }
}
