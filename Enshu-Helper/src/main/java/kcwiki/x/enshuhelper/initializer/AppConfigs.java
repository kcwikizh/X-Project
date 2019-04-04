/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.initializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author iHaru
 */
@Configuration
@PropertySource(value={"classpath:configuration/appconfig/appconfig.properties"})
public class AppConfigs {
    @Value("${global.useproxy}")
    private boolean allow_use_proxy;
    @Value("${global.proxyhost}")
    private String proxy_host;
    @Value("${global.proxyport}")
    private int proxy_port;
    @Value("${global.debug}")
    private boolean debug;
    @Value("${global.folder.privatedata}")
    private String folder_privatedata;
    @Value("${global.folder.publish}")
    private String folder_publish;
    @Value("${global.folder.workspace}")
    private String folder_workspace;
    @Value("${kcwiki.api.servers}")
    private String kcwiki_api_servers;
    @Value("${myprops.database.name}")
    private String database_name;
    @Value("${myprops.database.tables.userdata}")
    private String database_tables_userdata;
    @Value("${myprops.database.tables.systemparams}")
    private String database_tables_systemparams;
    @Value("${myprops.database.tables.systemlog}")
    private String database_tables_systemlog;
    @Value("${mail.serverhost}")
    private String mail_server_host;
    @Value("${mail.serverport}")
    private int mail_server_port;
    @Value("${mail.username}")
    private String mail_server_username;
    @Value("${mail.password}")
    private String mail_server_password;
    @Value("${mail.from}")
    private String mail_sender;
    @Value("${mail.to}")
    private String[] mail_recipient;
    @Value("${mail.title}")
    private String mail_title;
    @Value("${message.notice}")
    private String message_notice;

    /**
     * @return the allow_use_proxy
     */
    public boolean isAllow_use_proxy() {
        return allow_use_proxy;
    }

    /**
     * @param allow_use_proxy the allow_use_proxy to set
     */
    public void setAllow_use_proxy(boolean allow_use_proxy) {
        this.allow_use_proxy = allow_use_proxy;
    }

    /**
     * @return the proxy_host
     */
    public String getProxy_host() {
        return proxy_host;
    }

    /**
     * @param proxy_host the proxy_host to set
     */
    public void setProxy_host(String proxy_host) {
        this.proxy_host = proxy_host;
    }

    /**
     * @return the proxy_port
     */
    public int getProxy_port() {
        return proxy_port;
    }

    /**
     * @param proxy_port the proxy_port to set
     */
    public void setProxy_port(int proxy_port) {
        this.proxy_port = proxy_port;
    }

    /**
     * @return the debug
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * @param debug the debug to set
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * @return the folder_privatedata
     */
    public String getFolder_privatedata() {
        return folder_privatedata;
    }

    /**
     * @param folder_privatedata the folder_privatedata to set
     */
    public void setFolder_privatedata(String folder_privatedata) {
        this.folder_privatedata = folder_privatedata;
    }

    /**
     * @return the folder_publish
     */
    public String getFolder_publish() {
        return folder_publish;
    }

    /**
     * @param folder_publish the folder_publish to set
     */
    public void setFolder_publish(String folder_publish) {
        this.folder_publish = folder_publish;
    }

    /**
     * @return the folder_workspace
     */
    public String getFolder_workspace() {
        return folder_workspace;
    }

    /**
     * @param folder_workspace the folder_workspace to set
     */
    public void setFolder_workspace(String folder_workspace) {
        this.folder_workspace = folder_workspace;
    }

    /**
     * @return the mail_server_host
     */
    public String getMail_server_host() {
        return mail_server_host;
    }

    /**
     * @param mail_server_host the mail_server_host to set
     */
    public void setMail_server_host(String mail_server_host) {
        this.mail_server_host = mail_server_host;
    }

    /**
     * @return the mail_server_port
     */
    public int getMail_server_port() {
        return mail_server_port;
    }

    /**
     * @param mail_server_port the mail_server_port to set
     */
    public void setMail_server_port(int mail_server_port) {
        this.mail_server_port = mail_server_port;
    }

    /**
     * @return the mail_server_username
     */
    public String getMail_server_username() {
        return mail_server_username;
    }

    /**
     * @param mail_server_username the mail_server_username to set
     */
    public void setMail_server_username(String mail_server_username) {
        this.mail_server_username = mail_server_username;
    }

    /**
     * @return the mail_server_password
     */
    public String getMail_server_password() {
        return mail_server_password;
    }

    /**
     * @param mail_server_password the mail_server_password to set
     */
    public void setMail_server_password(String mail_server_password) {
        this.mail_server_password = mail_server_password;
    }

    /**
     * @return the mail_sender
     */
    public String getMail_sender() {
        return mail_sender;
    }

    /**
     * @param mail_sender the mail_sender to set
     */
    public void setMail_sender(String mail_sender) {
        this.mail_sender = mail_sender;
    }

    /**
     * @return the mail_recipient
     */
    public String[] getMail_recipient() {
        return mail_recipient;
    }

    /**
     * @param mail_recipient the mail_recipient to set
     */
    public void setMail_recipient(String[] mail_recipient) {
        this.setMail_recipient(mail_recipient);
    }

    /**
     * @return the mail_title
     */
    public String getMail_title() {
        return mail_title;
    }

    /**
     * @param mail_title the mail_title to set
     */
    public void setMail_title(String mail_title) {
        this.mail_title = mail_title;
    }

    /**
     * @return the message_notice
     */
    public String getMessage_notice() {
        return message_notice;
    }

    /**
     * @param message_notice the message_notice to set
     */
    public void setMessage_notice(String message_notice) {
        this.message_notice = message_notice;
    }

    /**
     * @return the kcwiki_api_servers
     */
    public String getKcwiki_api_servers() {
        return kcwiki_api_servers;
    }

    /**
     * @param kcwiki_api_servers the kcwiki_api_servers to set
     */
    public void setKcwiki_api_servers(String kcwiki_api_servers) {
        this.kcwiki_api_servers = kcwiki_api_servers;
    }

    /**
     * @return the database_name
     */
    public String getDatabase_name() {
        return database_name;
    }

    /**
     * @param database_name the database_name to set
     */
    public void setDatabase_name(String database_name) {
        this.database_name = database_name;
    }

    /**
     * @return the database_tables_systemparams
     */
    public String getDatabase_tables_systemparams() {
        return database_tables_systemparams;
    }

    /**
     * @param database_tables_systemparams the database_tables_systemparams to set
     */
    public void setDatabase_tables_systemparams(String database_tables_systemparams) {
        this.database_tables_systemparams = database_tables_systemparams;
    }

    /**
     * @return the database_tables_systemlog
     */
    public String getDatabase_tables_systemlog() {
        return database_tables_systemlog;
    }

    /**
     * @param database_tables_systemlog the database_tables_systemlog to set
     */
    public void setDatabase_tables_systemlog(String database_tables_systemlog) {
        this.database_tables_systemlog = database_tables_systemlog;
    }

    /**
     * @return the database_tables_userdata
     */
    public String getDatabase_tables_userdata() {
        return database_tables_userdata;
    }

    /**
     * @param database_tables_userdata the database_tables_userdata to set
     */
    public void setDatabase_tables_userdata(String database_tables_userdata) {
        this.database_tables_userdata = database_tables_userdata;
    }
    
    
}
