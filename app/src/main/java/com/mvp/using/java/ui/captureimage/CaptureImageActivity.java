package com.mvp.using.java.ui.captureimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import java.io.File;
import java.io.IOException;

public class CaptureImageActivity extends BaseActivity {

    public static final String TAG = CaptureImageActivity.class.getSimpleName();

    private static final int MULTIPLE_PERMISSION_REQUEST_CODE = 1001;
    private static final int MULTIPLE_PERMISSIONS_FROM_SETTING_REQUEST_CODE = 2001;
    private static final String[] MULTIPLE_PERMISSIONS =
            {
                    PermissionName.CAMERA,
                    PermissionName.READ_EXTERNAL_STORAGE,
            };

    private static final int SELECT_IMAGE_REQUEST_CODE = 3001;

    private ImageView imageView;
    private MaterialButton captureImageMaterialButton, selectImageMaterialButton;

    private ManagePermission managePermission;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_capture_image;
    }

    @Override
    protected void initializeView() {
        imageView = findViewById(R.id.imageView);
        captureImageMaterialButton = findViewById(R.id.captureImageMaterialButton);
        selectImageMaterialButton = findViewById(R.id.selectImageMaterialButton);
    }

    @Override
    protected void initializeObject() {
        managePermission = new ManagePermission(CaptureImageActivity.this);
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
        captureImageMaterialButton.setOnClickListener(this);
        selectImageMaterialButton.setOnClickListener(this);
    }

    @Override
    protected void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.captureImageMaterialButton:
                if (CameraUtils.isDeviceSupportCamera(CaptureImageActivity.this)) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                            /* Is Granted, Do next code */
                            createdFileUriAndCaptureImage();
                        } else {
                            /* If not granted, Request for Permission */
                            ActivityCompat.requestPermissions(
                                    CaptureImageActivity.this,
                                    MULTIPLE_PERMISSIONS,
                                    MULTIPLE_PERMISSION_REQUEST_CODE);
                        }
                    } else {
                        /* Already Granted, Do next code */
                        createdFileUriAndCaptureImage();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.selectImageMaterialButton:
                ImplicitIntentUtils.actionPickIntent(1, CaptureImageActivity.this, SELECT_IMAGE_REQUEST_CODE);
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
                                            CaptureImageActivity.this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(TAG, "camera permission denied and don't ask for it again");

                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            CaptureImageActivity.this,
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
                                            CaptureImageActivity.this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(TAG, "read external storage permission denied and don't ask for it again");

                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            CaptureImageActivity.this,
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
                    createdFileUriAndCaptureImage();
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
                createdFileUriAndCaptureImage();
            } else {
                Log.e(TAG, "permission is not granted, request for permission, from settings");
                ActivityCompat.requestPermissions(
                        CaptureImageActivity.this,
                        MULTIPLE_PERMISSIONS,
                        MULTIPLE_PERMISSION_REQUEST_CODE);
            }
        }

        if (requestCode == ImageAndVideoUtils.CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String realPath = RealPathUtils.getRealPath(this, imageUri);

                File oldFile = new File(realPath);
                System.out.println("=========================OLD===========================" + MemoryUtils.getReadableFileSize(oldFile.length()));

                /* this file is store in a File externalFilesDir = context.getExternalFilesDir("Compress"); directory */
                File newFile = ConfigureCompression.getInstance(this).compressToFile(oldFile);
                System.out.println("=========================NEW===========================" + MemoryUtils.getReadableFileSize(newFile.length()));

                Bitmap bitmap = BitmapFactory.decodeFile(newFile.getAbsolutePath());

                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }

                Toast.makeText(getApplicationContext(), ""+oldFile, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    ContentResolver contentResolver = getApplicationContext().getContentResolver();
                    ContentValues updateContentValue = new ContentValues();
                    updateContentValue.put(MediaStore.Images.Media.IS_PENDING, 1);
                    contentResolver.update(imageUri, updateContentValue, null, null);
                }
                Toast.makeText(getApplicationContext(), "User cancelled capture image", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == SELECT_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    /* Here read store permission require */
                    imageUri = data.getData();

                    if (imageUri != null) {
                        String realPath = RealPathUtils.getRealPath(this, imageUri);

                        File oldFile = new File(realPath);
                        System.out.println("=========================OLD===========================" + MemoryUtils.getReadableFileSize(oldFile.length()));

                        /* this file is store in a File externalFilesDir = context.getExternalFilesDir("Compress"); directory */
                        File newFile = ConfigureCompression.getInstance(this).compressToFile(oldFile);
                        System.out.println("=========================NEW===========================" + MemoryUtils.getReadableFileSize(newFile.length()));

                        Bitmap bitmap = BitmapFactory.decodeFile(newFile.getAbsolutePath());

                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }

                        Toast.makeText(getApplicationContext(), ""+newFile, Toast.LENGTH_SHORT).show();
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

    private void createdFileUriAndCaptureImage() {
        String customDirectoryName = "AppName";
        String extension = ".jpg";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri sourceUri = MediaStoreUtils.getSourceOfMedia(SourceOfMedia.EXTERNAL, MediaType.IMAGES);

            ContentResolver contentResolver = getContentResolver();

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.TITLE, MediaFileUtils.getRandomFileName(1));
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, MediaFileUtils.getRandomFileName(1) + extension);
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

            /* Add the date meta data to ensure the image is added at the front of the gallery */
            long millis = System.currentTimeMillis();
            contentValues.put(MediaStore.Images.Media.DATE_ADDED, millis / 1000L);
            contentValues.put(MediaStore.Images.Media.DATE_MODIFIED, millis / 1000L);

            contentValues.put(MediaStore.Images.Media.DATE_TAKEN, millis);
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + customDirectoryName);
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0);

            imageUri = contentResolver.insert(sourceUri, contentValues);
        } else {

            File mediaFile = null;

            try {
                mediaFile = MediaFileUtils.createFile(CaptureImageActivity.this, 1, customDirectoryName, extension);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageUri = FileProviderUtils.getFileUri(getApplicationContext(), mediaFile, AppConstants.PACKAGE_NAME);
        }

        ImageAndVideoUtils.cameraIntent(1, imageUri, CaptureImageActivity.this);
    }

    private void showPictureDialog(Activity activity) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(activity);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Capture photo from camera",
                "Select photo from gallery",
                "Select file",
                "Cancel"
        };

        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //takePhotoFromCamera();
                                break;
                            case 1:
                                // choosePhotoFromGallary();
                                break;
                            case 2:
                                //chooseDocumentFile();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
}