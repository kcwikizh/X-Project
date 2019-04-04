/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller.impl;

import kcwiki.x.enshuhelper.initializer.AppConfigs;
import kcwiki.x.enshuhelper.web.controller.BaseController;
import kcwiki.x.enshuhelper.web.controller.entity.BaseResponse;
import kcwiki.x.enshuhelper.web.controller.types.HttpRepStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping(value = {"/Message","/message"}, produces = "application/json;charset=UTF-8")
public class Message extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(Message.class);
    
    @Autowired
    AppConfigs appConfigs;
    
    @GetMapping(value = "/notice")
    @ResponseBody
    public BaseResponse notice()
    {
        return BaseResponseGen(HttpRepStatus.SUCCESS, appConfigs.getMessage_notice());
    }
    
}
