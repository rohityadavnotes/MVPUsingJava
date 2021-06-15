package com.mvp.using.java.ui.network;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.badasoftware.library.network.ApiListener;
import com.badasoftware.library.network.ApiResponse;
import com.badasoftware.library.network.helper.RxHelper;
import com.badasoftware.library.network.picasso.PicassoImageLoader;
import com.badasoftware.library.network.picasso.PicassoImageLoadingListener;
import com.mvp.using.java.R;
import com.mvp.using.java.data.repository.RemoteRepository;
import com.mvp.using.java.di.qualifier.remote.CustomRetrofit;
import com.mvp.using.java.model.direct.Base;
import com.mvp.using.java.model.withbase.Page;
import com.mvp.using.java.ui.base.BaseActivity;
import javax.inject.Inject;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;

public class RequestTestActivity extends BaseActivity implements HasAndroidInjector {

    public static final String TAG = RequestTestActivity.class.getSimpleName();

    private ImageView userImageView;
    private ProgressBar imageLoadingProgressBar, progressBar;
    private TextView responseTextView;
    private TextView sendRequestButtonTextView, cancelRequestButtonTextView;

    private Disposable subscribe;

    @Inject
    @CustomRetrofit
    public Retrofit retrofit;

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_request_test;
    }

    @Override
    protected void initializeView() {
        userImageView               = findViewById(R.id.userImageView);
        imageLoadingProgressBar     = findViewById(R.id.imageLoadingProgressBar);
        progressBar                 = findViewById(R.id.progressBar);
        progressBar                 = findViewById(R.id.progressBar);
        responseTextView            = findViewById(R.id.responseTextView);
        sendRequestButtonTextView   = findViewById(R.id.sendRequestButtonTextView);
        cancelRequestButtonTextView = findViewById(R.id.cancelRequestButtonTextView);
    }

    @Override
    protected void initializeObject() {
        /*GlideCacheUtil.getInstance().clearAllCache(this);

       GlideImageLoader.load(
                this,
                "https://backend24.000webhostapp.com/Json/profile.jpg",
                R.drawable.user_placeholder,
                R.drawable.error_placeholder,
                userImageView,
                new GlideImageLoadingListener() {
                    @Override
                    public void imageLoadSuccess() {
                        imageLoadingProgressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void imageLoadError() {
                        imageLoadingProgressBar.setVisibility(View.GONE);
                    }
                });*/

        PicassoImageLoader.load(
                this,
                "https://backend24.000webhostapp.com/Json/profile.jpg",
                R.drawable.user_placeholder,
                R.drawable.error_placeholder,
                userImageView,
                new PicassoImageLoadingListener() {
                    @Override
                    public void imageLoadSuccess() {
                        imageLoadingProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void imageLoadError(Exception exception) {
                        Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                        imageLoadingProgressBar.setVisibility(View.GONE);
                    }
                });
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
        sendRequestButtonTextView.setOnClickListener(this);
        cancelRequestButtonTextView.setOnClickListener(this);
    }

    @Override
    protected void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.sendRequestButtonTextView:
                getSingleEmployee();
               // getPage();
                break;
            case R.id.cancelRequestButtonTextView:
                RxHelper.getInstance().dispose(subscribe);
                break;
            default:
                break;
        }
    }

    private void getPage() {
        RemoteRepository.getInstance().getPage(RequestTestActivity.this, retrofit, new ApiListener<ApiResponse<Page>>() {
            @Override
            public void requestAccept(Disposable disposable) {
                subscribe = disposable;
            }

            @Override
            public void requestCancel() {
                responseTextView.setText("cancel");
            }

            @Override
            public void onSuccess(ApiResponse<Page> employeeBaseResponse) {
                responseTextView.setText(""+employeeBaseResponse.getData().getCurrentPageNumber());
            }

            @Override
            public void onError(String errorMessage) {
                responseTextView.setText(errorMessage);
            }
        });
    }

    private void getSingleEmployee() {
        RemoteRepository.getInstance().getEmployee(RequestTestActivity.this, retrofit, new ApiListener<Base>() {
            @Override
            public void requestAccept(Disposable disposable) {
                progressBar.setVisibility(View.VISIBLE);
                subscribe = disposable;
            }

            @Override
            public void requestCancel() {
                progressBar.setVisibility(View.GONE);
                responseTextView.setText("cancel");
            }

            @Override
            public void onSuccess(Base base) {
                progressBar.setVisibility(View.GONE);
                responseTextView.setText(base.getData().getEmployeeSalary());
            }

            @Override
            public void onError(String errorMessage) {
                progressBar.setVisibility(View.GONE);
                responseTextView.setText(errorMessage);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxHelper.getInstance().dispose(subscribe);
    }
}