package kcwiki.msgtransfer;

import java.io.IOException;
import kcwiki.msgtransfer.initializer.AppInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * https://www.baeldung.com/spring-boot-shutdown
 * https://github.com/spring-projects/spring-boot/issues/4657
*/
@SpringBootApplication
@ComponentScan(basePackages={"org.iharu", "kcwiki.msgtransfer"})
public class MessageTransferStationApplication
{
  private static final Logger LOG = LoggerFactory.getLogger(MessageTransferStationApplication.class);
  
  public static void main(String[] args)
    throws IOException
  {
    SpringApplication application = new SpringApplication(new Class[] { MessageTransferStationApplication.class });
    application.setBannerMode(Banner.Mode.OFF);
    application.setLogStartupInfo(false);
    application.setRegisterShutdownHook(false);
    ApplicationContext ctx = application.run(args);
    
    AppInitializer appInitializer = (AppInitializer)ctx.getBean("appInitializer");
    appInitializer.init();
    LOG.info("ACTIVE...");
  }
}
