/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kcwiki.x.enshuhelper.web.controller.entity.BaseResponse;
import kcwiki.x.enshuhelper.web.controller.types.HttpRepStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component("myAuthenctiationFailureHandler")
public class MyAuthentiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MyAuthentiationFailureHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        LOG.info("登录失败");

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(BaseResponseGen(HttpRepStatus.ERROR, exception.getMessage())));
    }
    
    protected BaseResponse BaseResponseGen(HttpRepStatus httpRepStatus, String msg) {
        BaseResponse respondBase = new BaseResponse();
        respondBase.setCode(httpRepStatus.getCode());
        respondBase.setStatus(httpRepStatus);
        respondBase.setInfo(msg);
        return respondBase;
    }
}
