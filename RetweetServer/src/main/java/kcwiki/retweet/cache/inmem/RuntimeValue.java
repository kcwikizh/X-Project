/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.cache.inmem;

import javax.annotation.PostConstruct;
import kcwiki.retweet.initializer.AppConfig;
import static org.iharu.constant.ConstantValue.FILESEPARATOR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class RuntimeValue {
    
    @Autowired
    AppConfig appConfig;
    
    public String APPROOT;
    public String WEBROOT_FOLDER;
    public String WEB_BASEURL;
    public String IMAGE_FOLDER;
    public String AVATAR_FOLDER;
    public String IMAGE_URL;
    public String AVATAR_URL;
    
    @PostConstruct
    public void initMethod() {
        WEBROOT_FOLDER = appConfig.getFolder_webroot();
        WEB_BASEURL = appConfig.getServer_baseurl();
        APPROOT = appConfig.getApplication_root();
        
        IMAGE_FOLDER =
            String.format("%s/%s", WEBROOT_FOLDER, appConfig.getFolder_web_image());
        AVATAR_FOLDER =
            String.format("%s/%s", WEBROOT_FOLDER, appConfig.getFolder_web_avatar());
        IMAGE_URL =
            String.format("file/image/");
        AVATAR_URL =
            String.format("file/avatar/");

    }
    
}
