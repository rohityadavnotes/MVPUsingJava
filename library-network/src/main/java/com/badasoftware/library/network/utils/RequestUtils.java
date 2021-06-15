package com.badasoftware.library.network.utils;

import androidx.annotation.NonNull;
import com.badasoftware.library.network.api.Service;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class RequestUtils {

    public static final String TAG = RequestUtils.class.getSimpleName();

    /**
     * Create request body for string
     */
    @NonNull
    public static RequestBody createRequestBodyForString(String string) {
        return RequestBody.create(string, MediaType.parse("text/plain"));
    }

    /**
     * Create request body for file
     */
    public static RequestBody createRequestBodyForFile(File file) {
        return RequestBody.create(file, MediaType.parse("multipart/form-data"));
    }

    /**
     * Create multipart part request parameter
     */
    public static MultipartBody.Part createMultipartBody(String fileParameter, String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = createRequestBodyForFile(file);
        return MultipartBody.Part.createFormData(fileParameter, file.getName(), requestBody);
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("aFile", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    public MultipartBody fileToMultipartBody(File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        builder.addFormDataPart("contentImage_", file.getName(), requestBody);

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    public Observable<ResponseBody> postJson(Service service, String url, Map<String, String> body) {
        return service.postJson(url, getRequestBody(body));
    }

    public Observable<ResponseBody> postJson(Service service, String url, JSONObject body) {
        return service.postJson(url, getRequestBody(body));
    }

    private RequestBody getRequestBody(Map<String, String> map) {
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8")
                , new Gson().toJson(map));
    }

    private RequestBody getRequestBody(JSONObject json) {
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8")
                , json.toString());
    }
}