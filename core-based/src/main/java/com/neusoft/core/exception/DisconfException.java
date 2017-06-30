package com.neusoft.core.exception;

/**
 * 类DisconfException.java的实现描述：配置错误
 * 
 * @author Administrator 2017年4月6日 上午10:13:32
 */
public class DisconfException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -3286184918471181645L;

    public DisconfException(){
        super();
    }

    public DisconfException(String message){
        super(message);
    }

    public DisconfException(String message, Throwable cause){
        super(message, cause);
    }

    public DisconfException(Throwable cause){
        super(cause);
    }
}
