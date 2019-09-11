/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.initializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author iHaru
 * http://www.baeldung.com/properties-with-spring#java
 */
@Configuration
//@PropertySource(value={"file:${user.dir}/configuration/appconfig/appconfig.properties"})
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
    @Value("${myprops.global.folder.web.image}")
    private String folder_web_image;
    @Value("${myprops.global.folder.web.avatar}")
    private String folder_web_avatar;
    @Value("${myprops.server.baseurl}")
    private String server_baseurl;
    @Value("${myprops.twitter.api}")
    private String twitter_api;
    @Value("${myprops.twitter.apisec}")
    private String twitter_apisec;
    @Value("${myprops.twitter.token}")
    private String twitter_token;
    @Value("${myprops.twitter.tokensec}")
    private String twitter_tokensec;
    @Value("${myprops.application.superuser.username}")
    private String application_superuser_username;
    @Value("${myprops.application.superuser.password}")
    private String application_superuser_password;
    @Value("${myprops.mail.from}")
    private String mail_sender;
    @Value("${myprops.mail.to}")
    private String[] mail_recipient;
    @Value("${myprops.mail.title}")
    private String mail_title;

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
    
    /**
     * @return the application_superuser_username
     */
    public String getApplication_superuser_username() {
        return application_superuser_username;
    }

    /**
     * @param application_superuser_username the application_superuser_username to set
     */
    public void setApplication_superuser_username(String application_superuser_username) {
        this.application_superuser_username = application_superuser_username;
    }

    /**
     * @return the application_superuser_password
     */
    public String getApplication_superuser_password() {
        return application_superuser_password;
    }

    /**
     * @param application_superuser_password the application_superuser_password to set
     */
    public void setApplication_superuser_password(String application_superuser_password) {
        this.application_superuser_password = application_superuser_password;
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
     * @return the folder_web_image
     */
    public String getFolder_web_image() {
        return folder_web_image;
    }

    /**
     * @param folder_web_image the folder_web_image to set
     */
    public void setFolder_web_image(String folder_web_image) {
        this.folder_web_image = folder_web_image;
    }

    /**
     * @return the folder_web_avatar
     */
    public String getFolder_web_avatar() {
        return folder_web_avatar;
    }

    /**
     * @param folder_web_avatar the folder_web_avatar to set
     */
    public void setFolder_web_avatar(String folder_web_avatar) {
        this.folder_web_avatar = folder_web_avatar;
    }

    /**
     * @return the server_baseurl
     */
    public String getServer_baseurl() {
        return server_baseurl;
    }

    /**
     * @param server_baseurl the server_baseurl to set
     */
    public void setServer_baseurl(String server_baseurl) {
        this.server_baseurl = server_baseurl;
    }

    /**
     * @return the twitter_api
     */
    public String getTwitter_api() {
        return twitter_api;
    }

    /**
     * @param twitter_api the twitter_api to set
     */
    public void setTwitter_api(String twitter_api) {
        this.twitter_api = twitter_api;
    }

    /**
     * @return the twitter_apisec
     */
    public String getTwitter_apisec() {
        return twitter_apisec;
    }

    /**
     * @param twitter_apisec the twitter_apisec to set
     */
    public void setTwitter_apisec(String twitter_apisec) {
        this.twitter_apisec = twitter_apisec;
    }

    /**
     * @return the twitter_token
     */
    public String getTwitter_token() {
        return twitter_token;
    }

    /**
     * @param twitter_token the twitter_token to set
     */
    public void setTwitter_token(String twitter_token) {
        this.twitter_token = twitter_token;
    }

    /**
     * @return the twitter_tokensec
     */
    public String getTwitter_tokensec() {
        return twitter_tokensec;
    }

    /**
     * @param twitter_tokensec the twitter_tokensec to set
     */
    public void setTwitter_tokensec(String twitter_tokensec) {
        this.twitter_tokensec = twitter_tokensec;
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

}
