package com.neusoft.core.exception;

/**
 * 类BizException.java的实现描述：业务异常类，用来封装检测异常
 * 
 * @author Administrator 2017年5月8日 上午10:14:13
 */
public class BizException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -2547022531964747685L;

    public BizException(){
        super();
    }

    public BizException(String message, Throwable cause){
        super(message, cause);
    }

    public BizException(String message){
        super(message);
    }

    public BizException(Throwable cause){
        super(cause);
    }

}
