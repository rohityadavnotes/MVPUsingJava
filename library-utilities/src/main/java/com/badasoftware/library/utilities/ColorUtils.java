package com.badasoftware.library.utilities;

import android.graphics.Color;
import java.util.Random;

public class ColorUtils {

    private ColorUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static int getRandomColor(int min , int max) {
        if (min>max){
            throw new IllegalArgumentException("min can not be greater than max");
        }
        int diff = max - min;
        Random random = new Random();
        int r = min + random.nextInt(diff);
        int g = min + random.nextInt(diff);
        int b = min + random.nextInt(diff);
        int a = min + random.nextInt(diff);
        return Color.argb(a,r,g,b);
    }

    public static int getRandomColor() {
        return getRandomColor(50,200);
    }

    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;     /* 50-199 */
        int green = random.nextInt(150) + 50;   /* 50-199 */
        int blue = random.nextInt(150) + 50;    /* 50-199 */
        return Color.rgb(red, green, blue);
    }
}
