/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.exception;

import kcwiki.x.enshuhelper.exception.types.KcServerStatus;
import kcwiki.x.enshuhelper.types.LogTypes;
import kcwiki.x.enshuhelper.exception.types.ServiceTypes;

/**
 *
 * @author iHaru
 */

public class BaseException extends RuntimeException {

    /**
     * @return the kcServerStatus
     */
    public KcServerStatus getKcServerStatus() {
        return kcServerStatus;
    }

    /**
     * @param kcServerStatus the kcServerStatus to set
     */
    public void setKcServerStatus(KcServerStatus kcServerStatus) {
        this.kcServerStatus = kcServerStatus;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 错误严重类型
     */
    private LogTypes logType;
    
    /**
     * 错误服务类型
     */
    private ServiceTypes serviceType;
    private KcServerStatus kcServerStatus;
    
    /**
     * 错误子编号
     */
    private Integer code;
    
    /**
     * 构造一个基本异常.
     *
     * @param message
     *            信息描述
     */
    public BaseException(String message)
    {
        super(message);
    }
    
    public BaseException(Throwable cause)
    {
        super(cause);
    }
    
    public BaseException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public BaseException(ServiceTypes serviceType, LogTypes logType)
    {
        this.logType = logType;
        this.serviceType = serviceType;
    }
    
    public BaseException(ServiceTypes serviceType, int code)
    {
        this.code = code;
        this.serviceType = serviceType;
    }
    
    public BaseException(ServiceTypes serviceType, String message)
    {
        super(message);
        this.serviceType = serviceType;
    }
    
    public BaseException(ServiceTypes serviceType, KcServerStatus kcServerStatus)
    {
        this.kcServerStatus = kcServerStatus;
        this.serviceType = serviceType;
    }
    
    public BaseException(ServiceTypes serviceType, KcServerStatus kcServerStatus, String message)
    {
        super(message);
        this.kcServerStatus = kcServerStatus;
        this.serviceType = serviceType;
    }
    
    public BaseException(ServiceTypes serviceType, LogTypes logType, String message)
    {
        super(message);
        this.logType = logType;
        this.serviceType = serviceType;
    }
    
    public BaseException(ServiceTypes serviceType, LogTypes logType, int code)
    {
        this.code = code;
        this.logType = logType;
        this.serviceType = serviceType;
    }
    
    public BaseException(ServiceTypes serviceType, LogTypes logType, int code, String message)
    {
        super(message);
        this.code = code;
        this.logType = logType;
        this.serviceType = serviceType;
    }
    
    public BaseException(ServiceTypes serviceType, int code, String message)
    {
        super(message);
        this.code = code;
        this.serviceType = serviceType;
    }


    /**
     * @return the logType
     */
    public LogTypes getMsgType() {
        return logType;
    }

    /**
     * @param logType the logType to set
     */
    public void setMsgType(LogTypes logType) {
        this.logType = logType;
    }

    /**
     * @return the serviceType
     */
    public ServiceTypes getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(ServiceTypes serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }
    
}
