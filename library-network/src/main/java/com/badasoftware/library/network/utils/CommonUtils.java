package com.badasoftware.library.network.utils;

public class CommonUtils {

    private CommonUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * is null or its length is 0
     *
     * isEmpty(null)        = true;
     * isEmpty("")          = true;
     * isEmpty("     ")     = false;
     * isEmpty("   Hello ") = false;
     * isEmpty("null")      = false;
     *
     * @param string
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(String string) {
        return ((string == null) || ("null".equalsIgnoreCase(string) || (string.trim().length() == 0)));
    }
}
