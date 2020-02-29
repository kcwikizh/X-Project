package kcwiki.management.controlcenter.initializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig
{
  @Value("${myprops.system.root}")
  private String system_root;
  @Value("${myprops.xtraffic.notifier.identity}")
  private String xtraffic_notifier_identity;
  @Value("${myprops.xtraffic.keepalive.enable}")
    private boolean xtraffic_keepalive_enable;
    @Value("${myprops.xtraffic.keepalive.period}")
    private int xtraffic_keepalive_period;
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
  @Value("${myprops.global.folder.download}")
  private String folder_download;
  @Value("${myprops.global.folder.publish}")
  private String folder_publish;
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
  @Value("${myprops.message.notice}")
  private String message_notice;
  @Value("${myprops.database.name}")
  private String myprops_database_name;

    /**
     * @return the system_root
     */
    public String getSystem_root() {
        return system_root;
    }

    /**
     * @param system_root the system_root to set
     */
    public void setSystem_root(String system_root) {
        this.system_root = system_root;
    }

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
     * @return the folder_download
     */
    public String getFolder_download() {
        return folder_download;
    }

    /**
     * @param folder_download the folder_download to set
     */
    public void setFolder_download(String folder_download) {
        this.folder_download = folder_download;
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
     * @return the myprops_database_name
     */
    public String getMyprops_database_name() {
        return myprops_database_name;
    }

    /**
     * @param myprops_database_name the myprops_database_name to set
     */
    public void setMyprops_database_name(String myprops_database_name) {
        this.myprops_database_name = myprops_database_name;
    }

    /**
     * @return the xtraffic_notifier_identity
     */
    public String getXtraffic_notifier_identity() {
        return xtraffic_notifier_identity;
    }

    /**
     * @param xtraffic_notifier_identity the xtraffic_notifier_identity to set
     */
    public void setXtraffic_notifier_identity(String xtraffic_notifier_identity) {
        this.xtraffic_notifier_identity = xtraffic_notifier_identity;
    }

    /**
     * @return the xtraffic_keepalive_enable
     */
    public boolean isXtraffic_keepalive_enable() {
        return xtraffic_keepalive_enable;
    }

    /**
     * @param xtraffic_keepalive_enable the xtraffic_keepalive_enable to set
     */
    public void setXtraffic_keepalive_enable(boolean xtraffic_keepalive_enable) {
        this.xtraffic_keepalive_enable = xtraffic_keepalive_enable;
    }

    /**
     * @return the xtraffic_keepalive_period
     */
    public int getXtraffic_keepalive_period() {
        return xtraffic_keepalive_period;
    }

    /**
     * @param xtraffic_keepalive_period the xtraffic_keepalive_period to set
     */
    public void setXtraffic_keepalive_period(int xtraffic_keepalive_period) {
        this.xtraffic_keepalive_period = xtraffic_keepalive_period;
    }
  
}
