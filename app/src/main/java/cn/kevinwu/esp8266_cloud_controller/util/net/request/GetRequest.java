package cn.kevinwu.esp8266_cloud_controller.util.net.request;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Request;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.kevinwu.esp8266_cloud_controller.util.net.MyNet;
import cn.kevinwu.esp8266_cloud_controller.util.net.callback.ICallback;


/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public class GetRequest extends IRequest{

    public GetRequest(String url){
        this.url = url;
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
        return null;
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

    private Request buildRequest(){
        if (url==null&&url.equals("")){
            new IllegalArgumentException("NETWORK : url can't be null !");
            return null;
        }
        Request.Builder request = new Request.Builder();
        request.url(url)
                .get();
        if(headers != null){
            for(Map.Entry<String ,String> map: headers.entrySet()){
                request.addHeader(map.getKey(),map.getValue());
            }
        }
        if(tag != null){
            request.tag(tag);
        }
        return request.build();
    }
}
