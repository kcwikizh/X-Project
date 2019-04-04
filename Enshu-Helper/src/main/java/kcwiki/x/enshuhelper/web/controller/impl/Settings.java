/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller.impl;

import kcwiki.x.enshuhelper.web.controller.BaseAuthorisedController;
import kcwiki.x.enshuhelper.web.controller.types.HttpRepStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import kcwiki.x.enshuhelper.web.controller.entity.BaseResponse;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping(value = {"/Settings","/settings"}, produces = "application/json;charset=UTF-8")
public class Settings extends BaseAuthorisedController {
    
    @PostMapping(value = "/kcsmaintenance")
    @ResponseBody
    public BaseResponse setKcsmaintenance()
    {
        if(!isLogined)
            return AuthorizationFailed();
        return BaseResponseGen(HttpRepStatus.SUCCESS, String.format("服务器维护设置目前的状态为：%s", true));
    }
    
}
