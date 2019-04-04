/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.cache.inmem;

import javax.annotation.PostConstruct;
import kcwiki.x.enshuhelper.initializer.AppConfigs;
import static kcwiki.x.enshuhelper.tools.ConstantValue.CLASSPATH;
import static kcwiki.x.enshuhelper.tools.ConstantValue.WEBROOT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class RuntimeValue {
    
    @Autowired
    AppConfigs appConfigs;
    
    public String PRIVATEDATA_FOLDER;
    public String DOWNLOAD_FOLDER;
    public String TEMPLATE_FOLDER;
    public String PUBLISH_FOLDER;
    public String WORKSPACE_FOLDER;
    public String FILE_SCANCORE;
    public String FILE_FFDEC;
    
    @PostConstruct
    public void initMethod() {
        PRIVATEDATA_FOLDER =
            String.format("%s%s", CLASSPATH, appConfigs.getFolder_privatedata());
    
        PUBLISH_FOLDER =
            String.format("%s/%s", WEBROOT, appConfigs.getFolder_publish());
        WORKSPACE_FOLDER =
            String.format("%s/%s", WEBROOT, appConfigs.getFolder_workspace());
        
    }
    
}
