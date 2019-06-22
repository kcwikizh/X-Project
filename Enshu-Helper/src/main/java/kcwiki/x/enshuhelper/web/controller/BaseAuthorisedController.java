/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kcwiki.x.enshuhelper.message.mail.EmailService;
import org.iharu.proto.web.WebResponseProto;
import static org.iharu.type.BaseHttpStatus.Unauthorized;
import org.iharu.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author iHaru
 */
@Component
public class BaseAuthorisedController extends BaseController  {
    private static final Logger LOG = LoggerFactory.getLogger(BaseAuthorisedController.class);
    protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
    protected boolean isLogined = false;
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    EmailService emailService;
  
    @ModelAttribute  
    public void checkAuthorization(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession(false);  
        Assert.notNull(request, "Request must not be null");  
        isLogined = (boolean) (WebUtils.getSessionAttribute(this.request, "isLogined") == null ? false:WebUtils.getSessionAttribute(request, "isLogined"));
//        this.isLogined = session.getAttribute("isLogined")== null ? false:"true".equals(session.getAttribute("isLogined"));
    }  

    protected WebResponseProto AuthorizationFailed(){
        return GenBaseResponse(Unauthorized, "请先登陆");
    }
}
