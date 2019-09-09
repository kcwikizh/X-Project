/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xtraffic.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author iHaru
 */
public class RandomUtils {
    
    public static String GenRandomString(int targetStringLength) {
  
        int leftLimit = 33;
        int rightLimit = 126;
        Random random = new SecureRandom();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            if(randomLimitedInt == 34 || randomLimitedInt == 39 || randomLimitedInt == 94 || randomLimitedInt == 96){
                i--;
                continue;
            }
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
