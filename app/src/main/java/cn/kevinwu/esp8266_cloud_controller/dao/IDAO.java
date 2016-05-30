package cn.kevinwu.esp8266_cloud_controller.dao;

/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public interface IDAO<M> {
    void loadingNet();
    void postingNet(M model);
}
