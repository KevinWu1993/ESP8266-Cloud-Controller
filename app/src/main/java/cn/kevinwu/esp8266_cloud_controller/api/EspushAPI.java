package cn.kevinwu.esp8266_cloud_controller.api;

/**
 * Created by KevinWu on 16-5-30.
 * KevinWu.cn
 */
public class EspushAPI {
    public static final String URL_HOST = "https://espush.cn/openapi/";

    public static final String URL_GET_APPS = URL_HOST + "apps/";

    public static final String URL_GET_DEVICE_LIST = URL_HOST + "openapi/devices/list/";

    public static final String URL_GET_IO_STATUS = URL_HOST + "gpio_status/";

    public static final String URL_REFRESH_DEVICE_ALIVE = URL_HOST + "manual_refresh/";

    public static final String URL_PUSH_MESSAGE = URL_HOST + "dev/push/message/";

    public static final String URL_SET_IO_EDGE = URL_HOST + "set_gpio_edge/";
}
