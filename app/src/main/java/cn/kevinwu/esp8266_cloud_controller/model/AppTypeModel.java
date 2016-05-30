package cn.kevinwu.esp8266_cloud_controller.model;

/**
 * Created by KevinWu on 16-5-30.
 * KevinWu.cn
 */
public class AppTypeModel implements IModel<AppTypeModel>{
    private String appid;
    private String appkey;
    private String name;
    private int online_num;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOnline_num() {
        return online_num;
    }

    public void setOnline_num(int online_num) {
        this.online_num = online_num;
    }

    @Override
    public void loadingNet() {

    }

    @Override
    public void postingNet(AppTypeModel model) {

    }
}
