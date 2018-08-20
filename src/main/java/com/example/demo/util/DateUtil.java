package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
        return format.format(new Date());
    }
}