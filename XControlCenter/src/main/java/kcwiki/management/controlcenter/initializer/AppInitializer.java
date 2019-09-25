package kcwiki.management.controlcenter.initializer;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kcwiki.management.controlcenter.cache.inmem.RuntimeValue;
import static kcwiki.management.controlcenter.constant.ConstantValue.TEMP_FOLDER;
import kcwiki.management.controlcenter.database.service.UtilsService;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.iharu.initializer.InitializerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements InitializerInterface 
{
    private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
    @Autowired
    AppConfig appConfig;
    @Autowired
    UtilsService utilsService;
    @Autowired
    RuntimeValue RUNTIME;
    boolean isInit = false;

    @PostConstruct
    @Override
    public void initMethod()
    {
      if (appConfig == null) {
        LOG.error("找不到程序主配置文件 程序初始化失败。");
      }
    }

    @PreDestroy
    @Override
    public void destroyMethod() {}

    @Override
    public void init()
      throws IOException
    {
      LOG.info("X-Project MessageTransferStation: initialization started");
      isInit = true;
      long startTime = System.currentTimeMillis();
      checkDatabase();
      long endTime = System.currentTimeMillis();
      LOG.info("AppRoot folder: {}", RUNTIME.APPROOT);
      LOG.info("Temp folder: {}", TEMP_FOLDER);
      LOG.info("WebRoot folder: {}", RUNTIME.WEBROOT_FOLDER);
      if (isInit)
      {
            kcwiki.management.controlcenter.cache.inmem.AppDataCache.isAppInit = true;
        LOG.info("X-Project MessageTransferStation: initialization completed in {} ms{}", endTime - startTime, LINESEPARATOR);
      }
      else
      {
        LOG.error("X-Project MessageTransferStation: initialization failed in {} ms{}", endTime - startTime, LINESEPARATOR);
        System.exit(0);
      }
    }
    
    private void checkDatabase() {
        utilsService.createModuleAuthorizationTable();
        utilsService.createModuleIdentityTable();
        utilsService.createModuleTokenTable();
        utilsService.createUserAuthenticationTable();
        utilsService.createAuthorizationLogTable();
        utilsService.createSystemLogTable();
    }
}
