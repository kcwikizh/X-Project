package kcwiki.management.xcontrolled.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XModuleConfig
{
    @Value("${myprops.xtraffic.force}")
    private boolean xtraffic_force;    
    @Value("${myprops.xtraffic.identity}")
    private String xtraffic_identity;   
    @Value("${myprops.xtraffic.token}")
    private String xtraffic_token;   
    @Value("${myprops.xtraffic.url.publickey}")
    private String xtraffic_url_publickey;
    @Value("${myprops.xtraffic.url.auth}")
    private String xtraffic_url_auth;
    @Value("${myprops.xtraffic.url.subscribe}")
    private String xtraffic_url_subscribe;
    @Value("${myprops.xtraffic.keepalive.enable}")
    private boolean xtraffic_keepalive_enable;
    @Value("${myprops.xtraffic.keepalive.period}")
    private int xtraffic_keepalive_period;

    /**
     * @return the xtraffic_url_publickey
     */
    public String getXtraffic_url_publickey() {
        return xtraffic_url_publickey;
    }

    /**
     * @param xtraffic_url_publickey the xtraffic_url_publickey to set
     */
    public void setXtraffic_url_publickey(String xtraffic_url_publickey) {
        this.xtraffic_url_publickey = xtraffic_url_publickey;
    }

    /**
     * @return the xtraffic_url_auth
     */
    public String getXtraffic_url_auth() {
        return xtraffic_url_auth;
    }

    /**
     * @param xtraffic_url_auth the xtraffic_url_auth to set
     */
    public void setXtraffic_url_auth(String xtraffic_url_auth) {
        this.xtraffic_url_auth = xtraffic_url_auth;
    }

    /**
     * @return the xtraffic_url_subscribe
     */
    public String getXtraffic_url_subscribe() {
        return xtraffic_url_subscribe;
    }

    /**
     * @param xtraffic_url_subscribe the xtraffic_url_subscribe to set
     */
    public void setXtraffic_url_subscribe(String xtraffic_url_subscribe) {
        this.xtraffic_url_subscribe = xtraffic_url_subscribe;
    }

    /**
     * @return the xtraffic_identity
     */
    public String getXtraffic_identity() {
        return xtraffic_identity;
    }

    /**
     * @param xtraffic_identity the xtraffic_identity to set
     */
    public void setXtraffic_identity(String xtraffic_identity) {
        this.xtraffic_identity = xtraffic_identity;
    }

    /**
     * @return the xtraffic_token
     */
    public String getXtraffic_token() {
        return xtraffic_token;
    }

    /**
     * @param xtraffic_token the xtraffic_token to set
     */
    public void setXtraffic_token(String xtraffic_token) {
        this.xtraffic_token = xtraffic_token;
    }

    /**
     * @return the xtraffic_force
     */
    public boolean isXtraffic_force() {
        return xtraffic_force;
    }

    /**
     * @param xtraffic_force the xtraffic_force to set
     */
    public void setXtraffic_force(boolean xtraffic_force) {
        this.xtraffic_force = xtraffic_force;
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
