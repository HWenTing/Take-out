package com.example.hp.materialtest.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HP on 2018/9/23.
 */

public class Time {
    public static String currentTime(){
        Calendar c1 = Calendar.getInstance();
        Date date = c1.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf1.format(date);
        return nowTime;
    }
}
