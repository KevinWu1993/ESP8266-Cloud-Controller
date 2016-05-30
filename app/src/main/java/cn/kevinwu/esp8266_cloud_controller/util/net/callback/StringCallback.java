package cn.kevinwu.esp8266_cloud_controller.util.net.callback;

import android.util.Log;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public abstract class StringCallback extends ICallback {
    public static final String TAG="StringCallback";
    public abstract void onSuccess(String result, int responseCode,Headers headers);
    public abstract void onFailure(String error);
    @Override
    public void onFailure(IOException e) {
        Log.e(TAG,e.toString());
        onFailure(e.toString());
    }

    @Override
    public void onResponse(Response response) throws IOException {
        onSuccess(response.body().string(),response.code(),response.headers());
    }
}
