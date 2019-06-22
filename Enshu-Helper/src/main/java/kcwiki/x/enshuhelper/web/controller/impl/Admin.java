/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller.impl;

import kcwiki.x.enshuhelper.web.controller.entity.AdminUser;
import org.iharu.proto.web.WebResponseProto;
import static org.iharu.type.BaseHttpStatus.Accepted;
import static org.iharu.type.BaseHttpStatus.Forbidden;
import org.iharu.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping(value = {"/47b080","/e3afed"}, produces = "application/json;charset=UTF-8")
public class Admin extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(Admin.class);
    
    
    @PostMapping(value = "/login")
    @ResponseBody
    public WebResponseProto login(AdminUser adminUser, @RequestBody String reqBody)
    {
        if(adminUser == null)
            return GenBaseResponse(Forbidden, Forbidden.getMsg());
        LOG.debug("uname:{}, upwd:{}", adminUser.getUsername(), adminUser.getPassword());
        if("xkcwiki".equals(adminUser.getUsername()) && "xkcwikipwd".equals(adminUser.getPassword())) {
            WebUtils.setSessionAttribute(request, "isLogined", true);
            return GenBaseResponse(Accepted, Accepted.getMsg());
        } 
        return GenBaseResponse(Forbidden, Forbidden.getMsg());
        
    }
}
