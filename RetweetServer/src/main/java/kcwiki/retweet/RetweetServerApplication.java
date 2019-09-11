package kcwiki.retweet;

import java.io.IOException;
import kcwiki.retweet.initializer.AppInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import twitter4j.TwitterException;

@SpringBootApplication()
@ComponentScan(basePackages = {"org.iharu", "kcwiki.retweet", "kcwiki.management.xcontrolled"})
//@ComponentScan(basePackages = {"org.iharu", "kcwiki.retweet"},
//            excludeFilters={@Filter(type=FilterType.ASSIGNABLE_TYPE, value=org.iharu.web.controller.DefaultGlobalController.class)})
public class RetweetServerApplication {
    private static final Logger LOG = LoggerFactory.getLogger(RetweetServerApplication.class);
    
    public static void main(String[] args) throws IOException, TwitterException {
        SpringApplication application = new SpringApplication(RetweetServerApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.setLogStartupInfo(false);
        application.setRegisterShutdownHook(false);
//        Map<String, Object> defaultProperties = new HashMap<>();
////        defaultProperties.put("logging.level.root", "ERROR");
//        application.setDefaultProperties(defaultProperties);
        ApplicationContext ctx = application.run(args);
//        
        AppInitializer appInitializer = (AppInitializer) ctx.getBean("appInitializer");
        appInitializer.init();
        LOG.info("ACTIVE...");
    }
}
