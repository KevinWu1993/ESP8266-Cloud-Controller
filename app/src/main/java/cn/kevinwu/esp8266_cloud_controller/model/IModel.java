package cn.kevinwu.esp8266_cloud_controller.model;

/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public interface IModel<M extends IModel> {
    void loadingNet();
    void postingNet(M model);
}
