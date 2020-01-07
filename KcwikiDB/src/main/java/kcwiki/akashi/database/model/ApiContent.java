/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.database.model;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import static org.springframework.util.Assert.notNull;

/**
 *
 * @author iHaru
 */
@Entity
@Table(name = "api_content")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({
        @TypeDef(name = "pgsql_enum",  typeClass = PostgreSQLEnumType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
public class ApiContent implements Serializable {
    private static long serialVersionUID = 1L;
    
    @Id
    @Column(name = "data_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;
    
    @Column(name = "data_type")
    private String dataType;
    @Column(name = "data_content")
    private String dataContent;
    @Column(name = "data_hash")
    private String hash;
    @Column(name = "create_timestamp", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
//    @CreationTimestamp
    private Date timestamp;
    
    public ApiContent(){}
    
    public ApiContent(String type, String content, String hash){
        notNull(type, "Method called with null parameter (type)");
        notNull(content, "Method called with null parameter (content)");
        notNull(hash, "Method called with null parameter (hash)");
        this.dataType = type;
        this.dataContent = content;
        this.hash = hash;
    }

    /**
     * @return the dataId
     */
    public Integer getDataId() {
        return dataId;
    }

    /**
     * @param dataId the dataId to set
     */
    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the dataContent
     */
    public String getDataContent() {
        return dataContent;
    }

    /**
     * @param dataContent the dataContent to set
     */
    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
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

}
