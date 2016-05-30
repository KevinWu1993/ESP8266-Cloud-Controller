package cn.kevinwu.esp8266_cloud_controller.util.common;

import android.util.Log;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 格式化获取时间
 * Created by KevinWu on 2016/2/5.
 */
public class TimeUtil {
    private static Calendar calendar;
    private static Date date;

    private static void setTime() {
        calendar = Calendar.getInstance();
        date = new Date();
        calendar.setTime(date);
    }

    /**
     * 返回格式示例：1454664319
     *
     * @return Long Timestamp
     * @author KevinWu
     * create at 2016/2/5 17:24
     */
    public static long getTimestamp() {
        setTime();
        long time = 0;
        time = date.getTime();
        return time/1000;
    }

    /**
     * 返回格式示例：16-01-12
     *
     * @return Formated String Time
     * @author KevinWu
     * create at 2016/2/5 17:39
     */
    public static String getYearMonthDay() {
        setTime();
        String year = "";
        String month = "";
        String day = "";
        DecimalFormat df = new DecimalFormat("00");
        year = df.format(calendar.get(Calendar.YEAR));
        month = df.format(calendar.get(Calendar.MONTH)+1);
        day = df.format(calendar.get(Calendar.DAY_OF_MONTH));
        return year + "-" + month + "-" + day;
    }

    /**
     * 返回格式示例：15
     *
     * @return Formated String Time
     * @author KevinWu
     * create at 2016/2/5 18:11
     */
    public static String getYear_xx() {
        setTime();
        String year = "";
        DecimalFormat df = new DecimalFormat("00");
        year = df.format(calendar.get(Calendar.YEAR));
        return year;
    }

    /**
     * 返回格式示例：2015
     *
     * @return Formated String Time
     * @author KevinWu
     * create at 2016/2/5 20:53
     */
    public static String getYear_xxxx() {
        setTime();
        String year = "";
        DecimalFormat df = new DecimalFormat("0000");
        year = df.format(calendar.get(Calendar.YEAR));
        return year;
    }

    /**
    *返回格式示例：05
    *@author KevinWu
    *create at 2016/2/5 20:55
    */
    public static String getMonth(){
        setTime();
        String month= "";
        DecimalFormat df = new DecimalFormat("00");
        month = df.format(calendar.get(Calendar.MONTH));
        return month;
    }

    /**
     * 取得当前时间在一个月中的第几号
    *返回格式示例：19
    *@author KevinWu
    *create at 2016/2/5 20:58
    */
    public static String getDay(){
        setTime();
        String day="";
        DecimalFormat df=new DecimalFormat("00");
        day=df.format(calendar.get(Calendar.DAY_OF_MONTH)+1);
        return  day;
    }

    /**
    *取得当前完整时间，精确到分
     * 返回格式示例：2015-12-08 19:30
    *@author KevinWu
    *create at 2016/2/5 20:59
    */
    public static String getFullTime(){
        setTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowTime=dateFormat.format(date);
        return nowTime;
    }

    /**
    *取得当前是周几，返回整型，周一到周日依次对应是1~7
    *@author KevinWu
    *create at 2016/2/5 21:17
    */
    public static int getDayOfWeek(){
        setTime();
        int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek==0)dayOfWeek=7;
        return dayOfWeek;
    }

    /***
     * 计算时间差，精确到天
     * 时间格式 yyyy-mm-dd
     * Created by MummyDing on 16-4-22.
     */

    public static String getTimeDiff(String dateStart,String dateEnd){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        boolean flag = true;
        try {
            Date d1 = df.parse(dateStart);
            Date d2 = df.parse(dateEnd);
            long diff = Math.abs(d2.getTime()-d1.getTime())/(1000*60*60*24);
            if (d2.getTime() < d1.getTime()){
                flag = false;
            }
            if (!flag){
                diff = (-diff);
            }
            return Long.toString(diff);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int getHour(){
        setTime();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }
    public static int getMinute(){
        setTime();
        int minute=calendar.get(Calendar.MINUTE);
        return minute;
    }

    public static int getHourMinute(){
        setTime();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        return hour*100+minute;
    }

    public static String getTerm(){
        String y=getYear_xxxx();
        int m = Integer.parseInt(getMonth());//月份
        m = (m >= 6 ? 9 : 3);
        String T = y + "/" + m + "/1 0:00:00";
        Log.d("取得的日期为：",T);
        return T;
    }


}
