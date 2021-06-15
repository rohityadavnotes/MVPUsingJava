package com.badasoftware.library.network.download;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import com.badasoftware.library.network.api.ServiceGenerator;
import com.badasoftware.library.network.api.download.DownloadService;
import com.badasoftware.library.network.download.write.FileWriteHandler;
import com.badasoftware.library.network.download.write.FileWriteThread;
import com.badasoftware.library.network.exception.RemoteException;
import com.badasoftware.library.network.rx.DisposablePool;
import com.badasoftware.library.utilities.file.RealPathUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DownloadFile {

    public static final String TAG = DownloadFile.class.getSimpleName();

    public static void begin(final Context context,
                             final Retrofit retrofit,
                             final String downloadFileFromThisUrl,
                             final Uri writeDownloadedFileOnThisUri,
                             final FileWriteHandler fileWriteHandler) {

        DownloadService downloadObserverService = ServiceGenerator.getInstance().createService(DownloadService.class, retrofit);
        Observable<Response<ResponseBody>> downloadObservable = downloadObserverService.downloadFile(downloadFileFromThisUrl);

        DownloadObserver<ResponseBody> downloadObserver = new DownloadObserver<ResponseBody>() {
            @Override
            public void onBegin(Disposable disposable) {
                DisposablePool.getInstance().add("download", disposable);
            }

            @Override
            public void onSuccess(Response<ResponseBody> response) {
                Log.i(TAG, "Download Success");
            }

            @Override
            public void onFailure(RemoteException remoteException) {
                Log.i(TAG, "Error 1 : "+remoteException.getDisplayMessage());

                Message message = fileWriteHandler.obtainMessage();
                message.what = FileWriteHandler.ERROR_WHEN_FILE_WRITING;

                Bundle bundle = new Bundle();
                bundle.putString(FileWriteHandler.ERROR, remoteException.getDisplayMessage());
                message.setData(bundle);

                fileWriteHandler.sendMessage(message);

                DisposablePool.getInstance().remove("download");
            }

            @Override
            public void onFinish() {
                Log.e(TAG,"All data emitted.");

             /*   *//* If require first send download location *//*
                Message message = fileWriteHandler.obtainMessage();
                message.what = FileWriteHandler.FILE_WRITING_COMPLETE;

                Bundle bundle = new Bundle();
                bundle.putString(FileWriteHandler.DOWNLOADED_FILE_LOCATION, RealPathUtils.getRealPath(context, writeDownloadedFileOnThisUri));
                message.setData(bundle);

                fileWriteHandler.sendMessage(message);*/

                DisposablePool.getInstance().remove("download");
            }
        };

        downloadObservable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(Response<ResponseBody> response) {
                        ResponseBody responseBody = response.body();
                        FileWriteThread fileWriteThread = new FileWriteThread(context, writeDownloadedFileOnThisUri, responseBody, fileWriteHandler);
                        Thread thread = new Thread(fileWriteThread);
                        thread.start();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d(TAG, "accept(Throwable throwable) : " + throwable.getMessage());

                        Message message = fileWriteHandler.obtainMessage();
                        message.what = FileWriteHandler.ERROR_WHEN_FILE_WRITING;

                        Bundle bundle = new Bundle();
                        bundle.putString(FileWriteHandler.ERROR, throwable.getMessage());
                        message.setData(bundle);

                        fileWriteHandler.sendMessage(message);
                    }
                })
               .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        /*   *//* If require first send download location *//*
                Message message = fileWriteHandler.obtainMessage();
                message.what = FileWriteHandler.FILE_WRITING_COMPLETE;

                Bundle bundle = new Bundle();
                bundle.putString(FileWriteHandler.DOWNLOADED_FILE_LOCATION, RealPathUtils.getRealPath(context, writeDownloadedFileOnThisUri));
                message.setData(bundle);

                fileWriteHandler.sendMessage(message);*/
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(downloadObserver);
    }
}
