package kcwiki.management.controlcenter.cache.inmem;

import javax.annotation.PostConstruct;
import kcwiki.management.controlcenter.initializer.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RuntimeValue
{
  @Autowired
  AppConfig appConfig;
  public String APPROOT;
  public String PRIVATEDATA_FOLDER;
  public String DOWNLOAD_FOLDER;
  public String STORAGE_FOLDER;
  public String TEMPLATE_FOLDER;
  public String PUBLISH_FOLDER;
  public String WEBROOT_FOLDER;
  
  @PostConstruct
  public void initMethod()
  {
    WEBROOT_FOLDER = appConfig.getFolder_webroot();
    APPROOT = appConfig.getSystem_root();
    
    PRIVATEDATA_FOLDER = String.format("%s/%s", APPROOT, appConfig.getFolder_privatedata());
    
    DOWNLOAD_FOLDER = String.format("%s/%s", APPROOT, appConfig.getFolder_download());
    
    PUBLISH_FOLDER = String.format("%s/%s", WEBROOT_FOLDER, appConfig.getFolder_publish());
  }
}
