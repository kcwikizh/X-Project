/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kcwiki.x.enshuhelper.exception.BaseException;
import kcwiki.x.enshuhelper.message.mail.EmailService;
import static kcwiki.x.enshuhelper.tools.ConstantValue.LINESEPARATOR;
import kcwiki.x.enshuhelper.types.LogTypes;
import kcwiki.x.enshuhelper.web.controller.entity.BaseResponse;
import kcwiki.x.enshuhelper.web.controller.types.HttpRepStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author x5171
 * https://blog.csdn.net/nmgrd/article/details/52734193
 */
@Service
public class BaseController <T>{
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
    protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    EmailService emailService;
  
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
        response.setHeader("Access-Control-Allow-Origin", "*");
    }  

    protected BaseResponse BaseResponseGen(HttpRepStatus httpRepStatus, String msg) {
        BaseResponse respondBase = new BaseResponse();
        respondBase.setCode(httpRepStatus.getCode());
        respondBase.setStatus(httpRepStatus);
        respondBase.setInfo(msg);
        return respondBase;
    }
    
    protected String Object2Json(Object obj) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LOG.error("转换数据时出错：{}, 原始数据为：{}", ExceptionUtils.getStackTrace(ex), obj);
            throw ex;
        }
    }
    
    @RequestMapping("*")
    public BaseResponse defaultResponse(){
        return BaseResponseGen(HttpRepStatus.FAILURE, "请求URL有误");
    }
    
    /**  
     * 用于处理异常的  
     * @param request
     * @param response
     * @param ex
     * @return  
     */  
    @ExceptionHandler({Exception.class, BaseException.class})  
    @ResponseBody
    public BaseResponse ExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        LOG.error("BaseController - ExceptionHandler left message： {}{}", LINESEPARATOR, ExceptionUtils.getStackTrace(ex));
        emailService.sendSimpleEmail(LogTypes.ERROR, String.format("服务器内部发生错误，请查阅日志文件以了解详情。具体错误信息如下：%s%s", 
                LINESEPARATOR, 
                ExceptionUtils.getStackTrace(ex)));
        BaseResponse respondBase = new BaseResponse();
        respondBase.setCode(-1);
        respondBase.setStatus(HttpRepStatus.ERROR);
        respondBase.setInfo("服务器内部发生错误");
        return respondBase;
    }

}
