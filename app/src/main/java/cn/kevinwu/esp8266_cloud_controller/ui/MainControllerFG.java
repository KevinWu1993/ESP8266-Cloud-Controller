package cn.kevinwu.esp8266_cloud_controller.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.serchinastico.coolswitch.CoolSwitch;
import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.kevinwu.esp8266_cloud_controller.MyApplication;
import cn.kevinwu.esp8266_cloud_controller.R;
import cn.kevinwu.esp8266_cloud_controller.api.EspushAPI;
import cn.kevinwu.esp8266_cloud_controller.config.SPStaticKey;
import cn.kevinwu.esp8266_cloud_controller.config.Setting;
import cn.kevinwu.esp8266_cloud_controller.event.E;
import cn.kevinwu.esp8266_cloud_controller.event.EventModel;
import cn.kevinwu.esp8266_cloud_controller.model.GPIOStatusRTBean;
import cn.kevinwu.esp8266_cloud_controller.util.common.SPUtil;
import cn.kevinwu.esp8266_cloud_controller.util.common.TimeUtil;
import cn.kevinwu.esp8266_cloud_controller.util.net.MyNet;
import cn.kevinwu.esp8266_cloud_controller.util.net.callback.JsonCallback;
import cn.kevinwu.esp8266_cloud_controller.util.sign.SignUtil;

/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public class MainControllerFG extends Fragment {
    public static final String TAG="MainControllerFG";
    private View parentView;
    private CoolSwitch ioTogglebtn;//控制io的开关
    private static int io14_Status = -1;//这里我用14控制插座的开关
    private TextView statusText;//显示状态的文本
    private AppCompatButton reFreshBtn;
    private ProgressBar progressBar;
    private String chipId;
    private Handler handle=new Handler(Looper.getMainLooper());


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.fragment_main_controller, null);
        initView();
        getStatus();
        return parentView;
    }





    private void initView() {
        ioTogglebtn = (CoolSwitch) parentView.findViewById(R.id.toggleButton);
        statusText = (TextView) parentView.findViewById(R.id.now_status);
        progressBar=(ProgressBar)parentView.findViewById(R.id.progressBar);
        reFreshBtn=(AppCompatButton)parentView.findViewById(R.id.status_refresh);
        ioTogglebtn.setChecked(true);
        ioTogglebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    lockBtn();
                    setStatus(Math.abs(io14_Status-1));
            }
        });
        statusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatus();
                statusText.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 防止按下两次开关，所以每次按下时锁住，返回结果再解锁
     */
    private void lockBtn() {
        ioTogglebtn.setClickable(false);
    }
    private void unLockBtn(){
        ioTogglebtn.setClickable(true);
    }



    /**
     * 设置开关按钮的状态
     */
    private void setTogglebtnAndText() {
        if(Setting.chipId==null||Setting.chipId.equals("")||Setting.chipId.equals("null")
                ||Setting.appid==null||Setting.appid.equals("")||Setting.appid.equals("null")){
            lockBtn();
            statusText.setText("请先设置芯片ID和AppID");
            return;
        }
        //高电平时继电器断开触点，
        // 所以此时插座智能控制端口断电
        if (io14_Status == 1) {
            ioTogglebtn.setChecked(true);
            unLockBtn();
            statusText.setText("智能插座状态：关闭");
        } else if(io14_Status==0) {
            ioTogglebtn.setChecked(false);
            unLockBtn();
            statusText.setText("智能插座状态：开启");
        }else if(io14_Status==-1){
            lockBtn();
            ioTogglebtn.setChecked(true);
            statusText.setText("网络异常，无法检测智能插座状态");
        }else{
            lockBtn();
            ioTogglebtn.setChecked(true);
            statusText.setText("无网络链接到插座，无法检测智能插座状态");
        }
        statusText.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
//        没建模，先实现功能，所以暂时不用eventbus通信
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //暂时把网络操作写在这，有待重构
    private void getStatus(){
        if(SPUtil.get(MyApplication.AppContext, SPStaticKey.PLUS_SP_FILE,SPStaticKey.CHIPID,"null").equals("null")
                ||SPUtil.get(MyApplication.AppContext, SPStaticKey.PLUS_SP_FILE,SPStaticKey.APPID,"null").equals("null")){
            setTogglebtnAndText();
        }else{
            Setting.chipId=(String)SPUtil.get(MyApplication.AppContext, SPStaticKey.PLUS_SP_FILE,SPStaticKey.CHIPID,"null");
            Setting.appid=(String)SPUtil.get(MyApplication.AppContext, SPStaticKey.PLUS_SP_FILE,SPStaticKey.APPID,"null");
        }
        String url= EspushAPI.URL_GET_IO_STATUS+ Setting.chipId+"/?sign="
                + SignUtil.getSign()+"&timestamp="+ TimeUtil.getTimestamp()+"&appid="+Setting.appid;
        MyNet.get(url)
                .enqueue(new JsonCallback<GPIOStatusRTBean>() {
                    @Override
                    public void onSuccess(final GPIOStatusRTBean entity, int responseCode, Headers headers) {
                        handle.post(new Runnable() {
                            @Override
                            public void run() {
                                if(entity.getResult()!=null){
                                    try {
                                        String result=URLEncoder.encode (entity.getResult(), "UTF-8" );
                                        Log.d(TAG,result);
                                        //其他都低电平，io14高电平状态时，返回%00%00%00%00%00%00%00%00%00%00%01%00
                                        //因为分割后io14在下标11的位置，所以先直接写死
                                        io14_Status=Integer.parseInt(result.split("%")[11]);
                                        Log.d(TAG,"--"+io14_Status);
                                        setTogglebtnAndText();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                        io14_Status=-1;
                                        setTogglebtnAndText();
                                    }

                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        handle.post(new Runnable() {
                            @Override
                            public void run() {
                                io14_Status=2;
                                setTogglebtnAndText();
                            }
                        });
                    }
                });
    }
    private void setStatus(int status){
        String url= EspushAPI.URL_SET_IO_EDGE+ Setting.chipId+"/14/"+status+"/?sign="
                + SignUtil.getSign()+"&timestamp="+ TimeUtil.getTimestamp()+"&appid="+Setting.appid;
        MyNet.get(url)
                .enqueue(new JsonCallback<GPIOStatusRTBean>() {
                    @Override
                    public void onSuccess(final GPIOStatusRTBean entity, int responseCode, Headers headers) {
                        handle.post(new Runnable() {
                            @Override
                            public void run() {
//                                if(entity.getResult()!=null){
//                                    try {
//                                        String result=URLEncoder.encode (entity.getResult(), "UTF-8" );
//                                        Log.d(TAG,"--"+io14_Status);
//                                        Log.d(TAG,result);
//                                        if(Integer.parseInt(result.split("%")[1])==0){
//                                            io14_Status=1;
//                                        }else{
//                                            io14_Status=0;
//                                        }
//                                        Log.d(TAG,"--"+io14_Status);
//                                        setTogglebtnAndText();
//                                    } catch (UnsupportedEncodingException e) {
//                                        e.printStackTrace();
//                                        io14_Status=-1;
//                                        setTogglebtnAndText();
//                                    }
//
//                                }
                                //直接再获取一次状态
                                getStatus();
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        handle.post(new Runnable() {
                            @Override
                            public void run() {
                                io14_Status=2;
                                setTogglebtnAndText();
                            }
                        });
                    }
                });
    }

}
