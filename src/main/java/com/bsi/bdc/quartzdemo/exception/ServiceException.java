package com.bsi.bdc.quartzdemo.exception;

/**
* DESCRIPTION:
* 服务异常处理
* @author zouyan
* @create 2019/11/11-下午4:02
* created by fuck~
**/
public class ServiceException extends Exception {

    public ServiceException(String msg, Exception e){
        super(msg + "\n" + e.getMessage());
    }

    public ServiceException(String msg){
        super(msg);
    }
}
