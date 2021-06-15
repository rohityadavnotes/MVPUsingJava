package com.badasoftware.library.utilities;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenerateFacebookKeyHashesUtils {

    private GenerateFacebookKeyHashesUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static String printKeyHash(Activity activity) {
        PackageInfo packageInfo;
        String key = null;
        try
        {
            /* getting application package name, as defined in manifest */
            String packageName = activity.getApplicationContext().getPackageName();

            packageInfo = activity.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);

            Log.e("Package Name = ", activity.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                Log.e("Key Hash = ", key);
            }
        }
        catch (PackageManager.NameNotFoundException exception)
        {
            Log.e("Name not found", exception.toString());
        }
        catch (NoSuchAlgorithmException exception) {
            Log.e("No such an algorithm", exception.toString());
        }
        catch (Exception exception) {
            Log.e("Exception", exception.toString());
        }
        return key;
    }
}
