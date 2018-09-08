/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author x5171
 */
public class CommontUtils {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CommontUtils.class);
    ObjectMapper objectMapper = new ObjectMapper();
    
    public String Object2Json(Object obj) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LOG.error("转换数据时出错：{}, 原始数据为：{}", ExceptionUtils.getStackTrace(ex), obj);
            throw ex;
        }
    }
    
    public static String UUIDGen() {
        return UUID.randomUUID().toString();
    }
    
    public static String getFileHex(String file){
        try {
            return DigestUtils.md5Hex(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CommontUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CommontUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Date CvtToUTC( Date date ){
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance() ;
        cal.setTime(date);
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }
    
}
