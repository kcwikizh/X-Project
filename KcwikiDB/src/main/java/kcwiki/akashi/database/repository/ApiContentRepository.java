/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.database.repository;

import kcwiki.akashi.database.model.ApiContent;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author iHaru
 */
public interface ApiContentRepository extends CrudRepository<ApiContent, Integer> {
    ApiContent findByDataTypeAndHash(String dataType, String hash);
//    ApiContent findTopByDataTypeByOrderByIdDesc(String dataType);
    ApiContent findTopByDataTypeOrderByTimestampDesc(String dataType);
    
}
