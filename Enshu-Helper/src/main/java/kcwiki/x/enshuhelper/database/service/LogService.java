/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.service;

import java.util.Date;
import kcwiki.x.enshuhelper.database.dao.LogDao;
import kcwiki.x.enshuhelper.database.entity.log.LogEntity;
import kcwiki.x.enshuhelper.exception.BaseException;
import kcwiki.x.enshuhelper.initializer.AppConfigs;
import static kcwiki.x.enshuhelper.tools.ConstantValue.LINESEPARATOR;
import kcwiki.x.enshuhelper.types.LogTypes;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author x5171
 */
@Service
public class LogService {
    
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private LogDao logDao;
    
    public boolean addLog(LogTypes msgTypes, String msg) {
        LogEntity logEntity = new LogEntity();
        logEntity.setType(msgTypes);
        logEntity.setTimestamp(new Date());
        logEntity.setMessage(msg);
        logDao.addLogMsg(appConfigs.getDatabase_tables_systemlog(), logEntity);
        return true;
    }
    
    public boolean addLog(LogTypes msgTypes, String signature, Throwable ex) {
        LogEntity logEntity = new LogEntity();
        logEntity.setType(msgTypes);
        logEntity.setTimestamp(new Date());
        logEntity.setSignature(signature);
        if(ex instanceof BaseException) {
            BaseException baseException = (BaseException) ex;
            String rs = ExceptionUtils.getStackTrace(ex);
            rs = String.format("%s%s%s%s%s", 
                    baseException.getServiceType().getName(), 
                    LINESEPARATOR,
                    baseException.getMessage(), 
                    LINESEPARATOR, 
                    rs);
            logEntity.setException(rs);
        }else {
            logEntity.setException(ExceptionUtils.getStackTrace(ex));
        }
        logDao.addLogMsg(appConfigs.getDatabase_tables_systemlog(), logEntity);
        return true;
    }
    
}
