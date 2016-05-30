package cn.kevinwu.esp8266_cloud_controller.util.net;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import cn.kevinwu.esp8266_cloud_controller.MyApplication;
import cn.kevinwu.esp8266_cloud_controller.util.net.request.GetRequest;
import cn.kevinwu.esp8266_cloud_controller.util.net.request.PostJsonRequest;


/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public class MyNet {
    public static OkHttpClient client;
    private static Gson gson;

    static{
        client=new OkHttpClient();
        gson=new Gson();
    }
    public static PostJsonRequest postJson(String url){
        return new PostJsonRequest(url);
    }
    public static GetRequest get(String url){
        return new GetRequest(url);
    }
    public static void cancelByTag(String tag){
        client.cancel(tag);
    }



    public static boolean isWIFI = false;

    /**
     * 读取网络状态
     * @return
     */
    public static boolean readNetworkState() {

        ConnectivityManager cm = (ConnectivityManager) MyApplication.AppContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            isWIFI = (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI);
            return true;
        } else {
            return false;
        }
    }
}
