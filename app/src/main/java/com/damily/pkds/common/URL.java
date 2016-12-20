package com.damily.pkds.common;

/**
 * Created by Dandan.Cao on 2016/7/27.
 */
public class URL {
    public static String HOST="http://172.22.35.164";
    public static String PORT="8060";
    public static String BASE_URL=HOST+":"+PORT;
    //设备报修
    public static String DEVICEFIX = BASE_URL + "/ams/device/maintain";
    //礼物说
    public static String  GIFT="http://api.liwushuo.com/v2/items?limit=30&offset=0&gender=1&generation=2";
}
