package com.badasoftware.library.utilities.file;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.List;

public class MediaUtils {

    private MediaUtils() {
        throw new UnsupportedOperationException("Should not create instance of Util class. Please use as static..");
    }

    /* Get All Apps that can capture image */
    public static List<Intent> getAllAppsThatCanCaptureImage(Activity activity, Uri captureImageStoreOnThisUri, String fileType) {
        List<Intent> intentList = new ArrayList<Intent>();
        PackageManager packageManager = activity.getPackageManager();

        Intent captureIntent = new Intent();

        if (fileType.equals("image/*"))
        {
            captureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        }

        if (fileType.equals("video/*"))
        {
            captureIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        }

        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(captureIntent, 0);

        for (ResolveInfo resolveInfo : resolveInfoList)
        {
            Intent intent = new Intent(captureIntent);
            intent.setPackage(resolveInfo.activityInfo.packageName);
            intent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));

            if (captureImageStoreOnThisUri != null)
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, captureImageStoreOnThisUri);
            }

            intentList.add(intent);
        }
        return intentList;
    }

    /* Get All Apps that can show image */
    public static Intent getAllAppsThatCanShowImage(String fileType) {
        Intent pickIntent = new Intent();
        pickIntent.setType(fileType);
        pickIntent.setAction(Intent.ACTION_PICK);
        return pickIntent;
    }

    /* Get All Apps that can provide document */
    public static List<Intent> getAllAppsThatCanProvideDocument(Activity activity, String fileType) {
        List<Intent> intentList = new ArrayList<Intent>();
        PackageManager packageManager = activity.getPackageManager();

        Intent documentIntent = new Intent(Intent.ACTION_GET_CONTENT);
        documentIntent.setType(fileType);

        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(documentIntent, 0);

        for (ResolveInfo resolveInfo : resolveInfoList)
        {
            Intent intent = new Intent(documentIntent);
            intent.setPackage(resolveInfo.activityInfo.packageName);
            intent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            intentList.add(intent);
        }
        return intentList;
    }

    /* Shows alert to user to select image either from camera or gallery */
    public static void chooserIntent(Activity activity, Uri  captureImageStoreOnThisUri, String fileType, int requestCode) {
        List<Intent> cameraIntents = new ArrayList<Intent>();
        if (fileType.equals("image/*") || fileType.equals("video/*")) {
            cameraIntents  = getAllAppsThatCanCaptureImage(activity, captureImageStoreOnThisUri, fileType) ;
        }

        Intent pickIntent           = getAllAppsThatCanShowImage(fileType);
        List<Intent> documentIntents= getAllAppsThatCanProvideDocument(activity, fileType);

        List<Intent> actionIntentList = new ArrayList<Intent>();
        if (cameraIntents.size() != 0) {
            actionIntentList.addAll(cameraIntents);
        }
        actionIntentList.addAll(documentIntents);

        Intent chooserIntent = Intent.createChooser(pickIntent, "Select Source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, actionIntentList.toArray(new Parcelable[]{}));
        activity.startActivityForResult(chooserIntent, requestCode);
    }

    public static void notifyMediaStoreScanner(Activity activity, Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        activity.sendBroadcast(mediaScanIntent);
    }
}
