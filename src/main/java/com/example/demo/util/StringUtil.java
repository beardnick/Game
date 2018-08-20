package com.example.demo.util;

public class StringUtil {

    public static boolean larger(String s1, String s2){
        Integer i1 = Integer.valueOf(s1);
        Integer i2 = Integer.valueOf(s2);
        return i1 > i2;
    }

    public static String max(String s1, String s2){
        return larger(s1, s2) ? s1 : s2;
    }

    public static String min(String s1, String s2){
        return larger(s1, s2) ? s2 : s1;
    }

    public static int ascend(String s1, String s2){
       return Integer.valueOf(s1) -  Integer.valueOf(s2);
    }

    public static int descend(String s1, String s2){
        return - ascend(s1, s2);
    }

}