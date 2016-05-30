package cn.kevinwu.esp8266_cloud_controller.util.net.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public abstract class JsonCallback<B> extends ICallback{
    public static final String TAG="JsonCallback";
    public abstract void onSuccess(B entity, int responseCode,Headers headers);
    public abstract void onFailure(String error);

    @Override
    public void onFailure(IOException e) {
        Log.e(TAG,e.toString());
        onFailure(e.toString());
    }

    @Override
    public void onResponse(Response response) throws IOException {
        try{
            B entity = new Gson().fromJson(response.body().string(),((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            onSuccess(entity,response.code(),response.headers());
        }catch (JsonSyntaxException e){
            Log.e(TAG,e.toString());
            onFailure("Data resolve error!");
        }
    }
}
