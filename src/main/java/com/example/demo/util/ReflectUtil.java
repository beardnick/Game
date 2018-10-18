package com.example.demo.util;

import java.lang.reflect.Method;

import static com.example.demo.util.StringUtil.upper;

public class ReflectUtil {

    public static  <T> Method getGetter(Class<T> cl, String filedName){
        filedName = upper(filedName);
        try {
            return cl.getMethod("get" + filedName);
        } catch (NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static  <T> Method getSetter(Class<T> cl, String filedName, Class<?> type){
        filedName = upper(filedName);
        try {
            return cl.getMethod("set" + filedName, type);
        } catch (NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
