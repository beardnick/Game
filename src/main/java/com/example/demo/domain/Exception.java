package com.example.demo.domain;

public class Exception {
    public String msg;

    public int status;

    public Exception(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }
}
