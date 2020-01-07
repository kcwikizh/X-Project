/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi;

import java.io.IOException;
import kcwiki.akashi.initializer.AppInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *
 * @author iHaru
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.iharu", "kcwiki.akashi", "kcwiki.management.xcontrolled"})
@EnableJpaAuditing
//@EnableScheduling
public class AkashiToolkitApplication {
    private static final Logger LOG = LoggerFactory.getLogger(AkashiToolkitApplication.class);
    
    public static void main(String[] args) throws IOException {
        SpringApplication application = new SpringApplication(AkashiToolkitApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.setLogStartupInfo(false);
        application.setRegisterShutdownHook(false);
        ApplicationContext ctx = application.run(args);

        AppInitializer appInitializer = (AppInitializer) ctx.getBean("appInitializer");
        appInitializer.init();
        LOG.info("ACTIVE...");
    }
}
