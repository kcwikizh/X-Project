package kcwiki.management.xtraffic.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleConfig
{
    @Value("${myprops.xtraffic.url.publickey}")
    private String xtraffic_url_publickey;
    @Value("${myprops.xtraffic.url.auth}")
    private String xtraffic_url_auth;
    @Value("${myprops.xtraffic.url.subscribe}")
    private String xtraffic_url_subscribe;

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
  
}
