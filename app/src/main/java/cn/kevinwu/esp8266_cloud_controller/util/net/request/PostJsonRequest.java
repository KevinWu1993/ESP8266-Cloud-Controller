package cn.kevinwu.esp8266_cloud_controller.util.net.request;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.util.LinkedHashMap;

import cn.kevinwu.esp8266_cloud_controller.util.net.MyNet;
import cn.kevinwu.esp8266_cloud_controller.util.net.callback.ICallback;


/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public class PostJsonRequest extends IRequest{
    public static final String TAG="JsonCallback";
    private static final MediaType Json = MediaType.parse("application/json; charset=utf-8");

    public PostJsonRequest(String url){
        this.url=url;
    }

    @Override
    public IRequest addHeaders(String key, String value) {
        if (headers == null){
            headers = new LinkedHashMap<>();
        }
        headers.put(key,value);
        return this;
    }

    @Override
    public IRequest addJsonObject(Object jsonBean) {
        this.json = new Gson().toJson(jsonBean);
        Log.d("封装后的Json数据为","--"+json.toString());
        return this;
    }

    @Override
    public IRequest addTag(String tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public void enqueue(ICallback callback) {
        Call call = MyNet.client.newCall(buildRequest());
        call.enqueue(callback);
    }
    private Request buildRequest() {
        if (url==null&&url.equals("")){
            new IllegalArgumentException("NETWORK : url can't be null !");
            return null;
        }
        RequestBody body = RequestBody.create(Json, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
