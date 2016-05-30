package cn.kevinwu.esp8266_cloud_controller.event;

/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public class EventModel<D> {
    private D data;

    private int eventCode=0;

    public EventModel(int eventCode,D data){
        this.eventCode=eventCode;
        this.data=data;
    }

    public EventModel(int eventCode){
        this(eventCode,null);
    }

    public int getEventCode() {
        return eventCode;
    }


    public D getData() {
        return data;
    }
}
