/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.cache.inmem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class AppDataCache {
    private static final Logger LOG = LoggerFactory.getLogger(AppDataCache.class);

    public static boolean IsAppInit = false;
    
    public static final Map<Long, String> AvatarMap = new HashMap();
    public static final Map<Long, Long> StatusMap = new HashMap();

}
