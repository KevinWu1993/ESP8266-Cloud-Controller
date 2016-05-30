package cn.kevinwu.esp8266_cloud_controller.util.net.callback;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

import java.io.IOException;

/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public abstract class ICallback implements Callback{
    public abstract void onFailure(IOException e);
    @Override
    public void onFailure(Request request, IOException e) {
        onFailure(e);
    }
}
