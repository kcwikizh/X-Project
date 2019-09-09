/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper;

import java.util.HashMap;
import java.util.Map;
import kcwiki.x.enshuhelper.cache.inmem.AppDataCache;
import kcwiki.x.enshuhelper.initializer.AppInitializer;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author iHaru
 */
@SpringBootApplication
//@MapperScan("kcwiki.x.enshuhelper.database.dao")  
@ComponentScan(basePackages = {"org.iharu", "kcwiki.x.enshuhelper", "kcwiki.management.xcontrolled"})
public class EnshuHelperApplication {
    private static final Logger LOG = LoggerFactory.getLogger(EnshuHelperApplication.class);
    
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EnshuHelperApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.setLogStartupInfo(false);
        application.setRegisterShutdownHook(false);
        Map<String, Object> defaultProperties = new HashMap<>();
//        defaultProperties.put("logging.level.root", "ERROR");
        application.setDefaultProperties(defaultProperties);
        ApplicationContext ctx = application.run(args);
        
        AppDataCache.ctx = ctx;
        
        AppInitializer appInitializer = (AppInitializer) ctx.getBean("appInitializer");
        appInitializer.init();
    }
}
