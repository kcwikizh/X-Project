/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.service;

import java.util.Date;
import kcwiki.x.enshuhelper.database.dao.LogDao;
import kcwiki.x.enshuhelper.database.entity.log.LogEntity;
import org.iharu.type.LogType;
import kcwiki.x.enshuhelper.initializer.AppConfig;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author iHaru
 */
@Service
public class LogService {
    
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private LogDao logDao;
    
    public boolean addLog(LogType msgTypes, String msg) {
        LogEntity logEntity = new LogEntity();
        logEntity.setType(msgTypes);
        logEntity.setTimestamp(new Date());
        logEntity.setMessage(msg);
        logDao.addLogMsg(appConfig.getDatabase_tables_systemlog(), logEntity);
        return true;
    }
    
    public boolean addLog(LogType msgTypes, String signature, Throwable ex) {
        LogEntity logEntity = new LogEntity();
        logEntity.setType(msgTypes);
        logEntity.setTimestamp(new Date());
        logEntity.setSignature(signature);
        if(ex instanceof BaseException) {
            BaseException baseException = (BaseException) ex;
            String rs = ExceptionUtils.getStackTrace(ex);
            rs = String.format("%s%s%s%s%s", 
                    baseException.getModule(), 
                    LINESEPARATOR,
                    baseException.getMessage(), 
                    LINESEPARATOR, 
                    rs);
            logEntity.setException(rs);
        }else {
            logEntity.setException(ExceptionUtils.getStackTrace(ex));
        }
        logDao.addLogMsg(appConfig.getDatabase_tables_systemlog(), logEntity);
        return true;
    }
    
}
