/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.controlcenter.database.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author iHaru
 */
@Service
public class ModuleService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ModuleService.class);
    
    public String GetIdentification(String token) {
        return null;
    }
    
    public List<String> GetAuthorizations(String token) {
        return new ArrayList();
    }
    
}
