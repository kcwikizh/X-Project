/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.cache.inmem;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import kcwiki.x.enshuhelper.core.entity.DiscordUserDataBO;
import kcwiki.x.enshuhelper.database.entity.UserDataDO;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author iHaru
 */
public class AppDataCache {
    public static boolean isAppInit = false;
    public static boolean isReadyReceive = false;
    
    public static LongAdder queryCount = new LongAdder();
    public static LongAdder matchCount = new LongAdder();
    
    public static final Map<Integer, String> gameWorlds = new ConcurrentHashMap<>();
    public static final Map<Integer, String> world_Num2Name = new ConcurrentHashMap<>();
    
    public static final Map<Integer, UserDataDO> matchCache = new ConcurrentHashMap<>();
    public static final Map<Integer, DiscordUserDataBO> discordMatchCache = new ConcurrentHashMap<>();
    
    public static final Map<String, Set<String>> discordAdmins = new ConcurrentHashMap();
    
    public static final Set<String> existTables = new HashSet<>();
    
    public static final Map<Integer, String> responseCache = new ConcurrentHashMap<>();
    
    public static ApplicationContext ctx;
    
    static{
        world_Num2Name.put(1, "横须贺镇守府");
        world_Num2Name.put(2, "呉镇守府");
        world_Num2Name.put(3, "佐世保镇守府");
        world_Num2Name.put(4, "舞鹤镇守府");
        world_Num2Name.put(5, "大凑警备府");
        world_Num2Name.put(6, "トラック泊地");
        world_Num2Name.put(7, "リンガ泊地");
        world_Num2Name.put(8, "ラバウル基地");
        world_Num2Name.put(9, "ショートランド泊地");
        world_Num2Name.put(10, "ブイン基地");
        world_Num2Name.put(11, "タウイタウイ泊地");
        world_Num2Name.put(12, "パラオ泊地");
        world_Num2Name.put(13, "ブルネイ泊地");
        world_Num2Name.put(14, "単冠湾泊地");
        world_Num2Name.put(15, "幌筵泊地");
        world_Num2Name.put(16, "宿毛湾泊地");
        world_Num2Name.put(17, "鹿屋基地");
        world_Num2Name.put(18, "岩川基地");
        world_Num2Name.put(19, "佐伯湾泊地");
        world_Num2Name.put(20, "柱岛泊地");
    }
}
