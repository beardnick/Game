package com.example.demo.util;

import com.example.demo.domain.Const;
import com.example.demo.domain.Exception;
import com.example.demo.domain.Response;

public class ResponseUtil {

    protected Response response;

    public static Response result(Object o, int status, String msg){
        if(o == null){
            return result(Const.NOT_FOUND);
        }else {
            return new Response(o, msg, status);
        }
    }

    public static Response result(int status, String msg){
        return new Response(null, msg, status);
    }


    public static Response result(Exception e){
        return new Response(null, e.msg, e.status);
    }


}
