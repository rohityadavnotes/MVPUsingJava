package com.mvp.using.java.ui.capturevideo;

import android.os.Bundle;
import android.view.View;
import com.mvp.using.java.R;
import com.mvp.using.java.ui.base.BaseActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.badasoftware.library.utilities.CameraUtils;
import com.badasoftware.library.utilities.ImageAndVideoUtils;
import com.badasoftware.library.utilities.ImplicitIntentUtils;
import com.badasoftware.library.utilities.compress.ConfigureCompression;
import com.badasoftware.library.utilities.file.FileProviderUtils;
import com.badasoftware.library.utilities.file.MediaFileUtils;
import com.badasoftware.library.utilities.file.MemoryUtils;
import com.badasoftware.library.utilities.file.RealPathUtils;
import com.badasoftware.library.utilities.mediastore.MediaStoreUtils;
import com.badasoftware.library.utilities.mediastore.MediaType;
import com.badasoftware.library.utilities.mediastore.SourceOfMedia;
import com.badasoftware.library.utilities.permissionutils.ManagePermission;
import com.badasoftware.library.utilities.permissionutils.PermissionDialog;
import com.badasoftware.library.utilities.permissionutils.PermissionName;
import com.google.android.material.button.MaterialButton;
import com.mvp.using.java.R;
import com.mvp.using.java.constants.AppConstants;
import com.mvp.using.java.ui.base.BaseActivity;
import com.mvp.using.java.ui.captureimage.CaptureImageActivity;

import java.io.File;
import java.io.IOException;

public class CaptureVideoActivity extends BaseActivity {

    public static final String TAG = CaptureVideoActivity.class.getSimpleName();

    private static final int MULTIPLE_PERMISSION_REQUEST_CODE = 1001;
    private static final int MULTIPLE_PERMISSIONS_FROM_SETTING_REQUEST_CODE = 2001;
    private static final String[] MULTIPLE_PERMISSIONS =
            {
                    PermissionName.CAMERA,
                    PermissionName.READ_EXTERNAL_STORAGE,
            };

    private static final int SELECT_VIDEO_REQUEST_CODE = 3001;

    private VideoView videoView;
    private MaterialButton captureVideoMaterialButton, selectVideoMaterialButton;

    private ManagePermission managePermission;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_capture_video;
    }

    @Override
    protected void initializeView() {
        videoView                   = findViewById(R.id.videoView);
        captureVideoMaterialButton  = findViewById(R.id.captureVideoMaterialButton);
        selectVideoMaterialButton   = findViewById(R.id.selectVideoMaterialButton);
    }

    @Override
    protected void initializeObject() {
        managePermission = new ManagePermission(CaptureVideoActivity.this);
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
        captureVideoMaterialButton.setOnClickListener(this);
        selectVideoMaterialButton.setOnClickListener(this);
    }

    @Override
    protected void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.captureVideoMaterialButton:
                if (CameraUtils.isDeviceSupportCamera(CaptureVideoActivity.this)) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                            /* Is Granted, Do next code */
                            createdFileUriAndCaptureVideo();
                        } else {
                            /* If not granted, Request for Permission */
                            ActivityCompat.requestPermissions(
                                    CaptureVideoActivity.this,
                                    MULTIPLE_PERMISSIONS,
                                    MULTIPLE_PERMISSION_REQUEST_CODE);
                        }
                    } else {
                        /* Already Granted, Do next code */
                        createdFileUriAndCaptureVideo();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.selectVideoMaterialButton:
                ImplicitIntentUtils.actionPickIntent(2, CaptureVideoActivity.this, SELECT_VIDEO_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            String permission = permissions[i];

                            if (permission.equalsIgnoreCase(PermissionName.CAMERA)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e(TAG, "camera permission denied");

                                    ActivityCompat.requestPermissions(
                                            CaptureVideoActivity.this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(TAG, "camera permission denied and don't ask for it again");

                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            CaptureVideoActivity.this,
                                            R.drawable.permission_ic_camera,
                                            "Camera Permission",
                                            "Kindly allow Camera Permission from Settings, without this permission the app is unable to provide photo capture feature. Please turn on permissions at [Setting] -> [Permissions]>",
                                            permission,
                                            MULTIPLE_PERMISSIONS_FROM_SETTING_REQUEST_CODE);
                                    return;
                                }
                            }

                            if (permission.equalsIgnoreCase(PermissionName.READ_EXTERNAL_STORAGE)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e(TAG, "read external storage permission denied");

                                    ActivityCompat.requestPermissions(
                                            CaptureVideoActivity.this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(TAG, "read external storage permission denied and don't ask for it again");

                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            CaptureVideoActivity.this,
                                            R.drawable.permission_ic_storage,
                                            "Read Storage Permission",
                                            "Kindly allow Read Storage Permission from Settings, without this permission the app is unable to provide file read feature. Please turn on permissions at [Setting] -> [Permissions]>",
                                            permission,
                                            MULTIPLE_PERMISSIONS_FROM_SETTING_REQUEST_CODE);
                                    return;
                                }
                            }
                        }
                    }
                    Log.e(TAG, "all permission granted, do the task");
                    createdFileUriAndCaptureVideo();
                } else {
                    Log.e(TAG, "Unknown Error");
                }
                break;
            default:
                throw new RuntimeException("unhandled permissions request code: " + requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MULTIPLE_PERMISSIONS_FROM_SETTING_REQUEST_CODE) {
            if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                Log.e(TAG, "permission granted from settings");
                createdFileUriAndCaptureVideo();
            } else {
                Log.e(TAG, "permission is not granted, request for permission, from settings");
                ActivityCompat.requestPermissions(
                        CaptureVideoActivity.this,
                        MULTIPLE_PERMISSIONS,
                        MULTIPLE_PERMISSION_REQUEST_CODE);
            }
        }

        if (requestCode == ImageAndVideoUtils.CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (videoUri != null) {
                    String realPath = RealPathUtils.getRealPath(this, videoUri);
                    videoView.setVideoURI(videoUri);
                    videoView.start();
                    Toast.makeText(getApplicationContext(), ""+realPath, Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    ContentResolver contentResolver = getApplicationContext().getContentResolver();
                    ContentValues updateContentValue = new ContentValues();
                    updateContentValue.put(MediaStore.Video.Media.IS_PENDING, 1);
                    contentResolver.update(videoUri, updateContentValue, null, null);
                }
                Toast.makeText(getApplicationContext(), "User cancelled capture image", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == SELECT_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    /* Here read store permission require */
                    videoUri = data.getData();
                    if (videoUri != null) {
                        String realPath = RealPathUtils.getRealPath(this, videoUri);
                        videoView.setVideoURI(videoUri);
                        videoView.start();
                        Toast.makeText(getApplicationContext(), ""+realPath, Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "User cancelled select image", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sorry! Failed to select image", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void createdFileUriAndCaptureVideo() {
        String customDirectoryName = "AppName";
        String extension = ".mp4";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri sourceUri = MediaStoreUtils.getSourceOfMedia(SourceOfMedia.EXTERNAL, MediaType.VIDEO);

            ContentResolver contentResolver = getContentResolver();

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.TITLE, MediaFileUtils.getRandomFileName(2));
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, MediaFileUtils.getRandomFileName(2) + extension);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");

            /* Add the date meta data to ensure the image is added at the front of the gallery */
            long millis = System.currentTimeMillis();
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, millis / 1000L);
            contentValues.put(MediaStore.Video.Media.DATE_MODIFIED, millis / 1000L);

            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, millis);
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_MOVIES + "/" + customDirectoryName);
            contentValues.put(MediaStore.Video.Media.IS_PENDING, 0);

            videoUri = contentResolver.insert(sourceUri, contentValues);
        } else {

            File mediaFile = null;

            try {
                mediaFile = MediaFileUtils.createFile(CaptureVideoActivity.this, 2, customDirectoryName, extension);
            } catch (IOException e) {
                e.printStackTrace();
            }

            videoUri = FileProviderUtils.getFileUri(getApplicationContext(), mediaFile, AppConstants.PACKAGE_NAME);
        }

        ImageAndVideoUtils.cameraIntent(2, videoUri, CaptureVideoActivity.this);
    }
}