package com.badasoftware.library.utilities.file;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public class CleanUtils {

    private CleanUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * Clear the internal cache of this application (/data/data/com.xxx.xxx/cache)
     *
     * @param context context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * Clear all databases of this application (/data/data/com.xxx.xxx/databases)
     *
     * @param context context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File(context.getFilesDir().getPath()
                + context.getPackageName() + "/databases"));
    }

    /**
     * Clear this application SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     *
     * @param context context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File(context.getFilesDir().getPath()
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * Clear this application database by name
     *
     * @param context context
     * @param dbName  database name
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * Clear the content under /data/data/com.xxx.xxx/files
     *
     * @param context context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * Clear the content under the external cache (/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * Clear the files in the custom path, use caution, please don't delete by mistake. And only supports the deletion of files in the directory
     *
     * @param filePath file path
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * Clear all data in this app
     *
     * @param context  context
     * @param filePath file path
     */
    public static void cleanApplicationData(Context context, String... filePath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String fp : filePath) {
            cleanCustomCache(fp);
        }
    }

    /**
     * Deleting method Only the files in a certain folder will be deleted here. This operation is more dangerous, please use it with caution;
     *
     * @param directory Folder File object
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                /* delete file and folder. It's dangerous! */
                if (item.isDirectory()) {
                    deleteFilesByDirectory(item);
                } else {
                    item.delete();
                }
            }
        }
    }

    /**
     * Get file size
     * <p>
     * Context.getExternalFilesDir() --> SDCard/Android/data/The package name of your application/files/ directory, generally put some long-term saved data
     * Context.getExternalCacheDir() --> SDCard/Android/data/your application package name/cache/ directory, generally store temporary cache data
     */
    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] list = file.listFiles();
            if (list != null) {
                for (File temp : list) {
                    /* If there are files below */
                    if (temp.isDirectory()) {
                        size = size + getFolderSize(temp);
                    } else {
                        size = size + temp.length();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * Get cache size
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return MemoryUtils.getReadableFileSize(cacheSize);
    }
}
