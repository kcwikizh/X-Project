/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.core.entity;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import kcwiki.akashi.web.entity.type.DataType;
import org.iharu.util.JsonUtils;

/**
 *
 * @author iHaru
 */
public class ApiDataBO<T> {
    private DataType dataType;
    private long timestamp;
    private String hash;
    private T data;
    
    public ApiDataBO() {}
    
    public ApiDataBO(DataType dataType, T data) {
        this.dataType = dataType;
        this.data = data;
        this.timestamp = new Date().getTime();
        this.hash = Hashing.sha256()
                        .hashString(JsonUtils.object2json(data), StandardCharsets.UTF_8)
                        .toString();
    }
    
    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return the dataType
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}
