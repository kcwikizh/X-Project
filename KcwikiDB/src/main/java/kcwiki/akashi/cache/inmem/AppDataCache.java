/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.cache.inmem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kcwiki.akashi.core.entity.ApiDataBO;
import kcwiki.akashi.web.entity.type.DataType;
import org.iharu.proto.web.WebResponseProto;

/**
 *
 * @author iHaru
 */
public class AppDataCache {
    
    public static boolean isAppInit = false;
    
    public static final Map<DataType, ApiDataBO> API_DATA_CACHE = new ConcurrentHashMap();
    public static final Map<String, WebResponseProto> API_RESPONSE_CACHE = new ConcurrentHashMap();
    
}
