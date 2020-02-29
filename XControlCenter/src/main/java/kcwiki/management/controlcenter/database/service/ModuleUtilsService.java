/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.controlcenter.database.service;

import java.util.List;
import kcwiki.management.controlcenter.database.dao.ModuleUtilsMapper;
import kcwiki.management.controlcenter.database.entity.ModuleAuthorization;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author iHaru
 */
@Service
public class ModuleUtilsService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ModuleUtilsService.class);
    
    @Autowired
    private ModuleUtilsMapper moduleUtilsMapper;
    
    public String getIdentification(String token) {
        try{
            return moduleUtilsMapper.getIdentification(token);
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        return null;
    }
    
    public List<ModuleAuthorization> getAuthorizations(String token) {
        return moduleUtilsMapper.getAuthorizations(token);
    }
    
    public Integer deleteToken(String token) {
        return moduleUtilsMapper.deleteToken(token);
    }
    
    public Integer removeAuthorization(String token, String identity) {
        return moduleUtilsMapper.removeAuthorization(token, identity);
    }
    
    public Integer removeIdentity(String identity) {
        return moduleUtilsMapper.removeIdentity(identity);
    }
    
}
