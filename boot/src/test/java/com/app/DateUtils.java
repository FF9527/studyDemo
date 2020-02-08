package com.app;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:wuqi
 * @date:2020/2/8
 * @description:com.app
 * @version:1.0
 */
public class DateUtils {

    private DateUtils(){

    }

    public static String format(long time){
        Date date = new Date(time);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
