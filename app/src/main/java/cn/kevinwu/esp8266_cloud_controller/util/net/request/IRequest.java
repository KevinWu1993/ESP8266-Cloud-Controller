package cn.kevinwu.esp8266_cloud_controller.util.net.request;

import java.util.Map;

import cn.kevinwu.esp8266_cloud_controller.util.net.callback.ICallback;


/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public abstract class IRequest {
    protected Map<String,String> headers;
    protected String url = null;
    protected String tag = null;
    protected String json;
    public abstract IRequest addHeaders(String key,String value);
    public abstract IRequest addJsonObject(Object jsonBean);
    public abstract IRequest addTag(String tag);
    public abstract void enqueue(ICallback callback);
}
