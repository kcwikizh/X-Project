/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.initializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author iHaru
 */
@Configuration
public class AppConfig {
    @Value("${myprops.application.root}")
    private String application_root;
    @Value("${myprops.global.useproxy}")
    private boolean allow_use_proxy;
    @Value("${myprops.global.proxyhost}")
    private String proxy_host;
    @Value("${myprops.global.proxyport}")
    private int proxy_port;
    @Value("${myprops.global.debug}")
    private boolean debug;
    @Value("${myprops.global.folder.webroot}")
    private String folder_webroot;
    @Value("${myprops.global.folder.privatedata}")
    private String folder_privatedata;
    @Value("${myprops.global.folder.publish}")
    private String folder_publish;
    @Value("${myprops.global.folder.workspace}")
    private String folder_workspace;
    @Value("${myprops.global.kcwiki.api.servers}")
    private String kcwiki_api_servers;
    @Value("${myprops.mail.from}")
    private String mail_sender;
    @Value("${myprops.mail.to}")
    private String[] mail_recipient;
    @Value("${myprops.mail.title}")
    private String mail_title;
    @Value("${myprops.message.notice}")
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
        this.mail_recipient = mail_recipient;
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
     * @return the application_root
     */
    public String getApplication_root() {
        return application_root;
    }

    /**
     * @param application_root the application_root to set
     */
    public void setApplication_root(String application_root) {
        this.application_root = application_root;
    }

    /**
     * @return the folder_webroot
     */
    public String getFolder_webroot() {
        return folder_webroot;
    }

    /**
     * @param folder_webroot the folder_webroot to set
     */
    public void setFolder_webroot(String folder_webroot) {
        this.folder_webroot = folder_webroot;
    }
    
}
