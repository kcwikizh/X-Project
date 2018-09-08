/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.security.config;

import kcwiki.x.enshuhelper.web.security.handler.MyAuthentiationFailureHandler;
import kcwiki.x.enshuhelper.web.security.handler.MyAuthentiationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author x5171
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired 
    MyAuthentiationSuccessHandler myAuthentiationSuccessHandler;
    @Autowired 
    MyAuthentiationFailureHandler myAuthentiationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
                .successHandler(myAuthentiationSuccessHandler) // 自定义登录成功处理 
                .failureHandler(myAuthentiationFailureHandler) // 自定义登录失败处理
                .loginPage("/login.html")           // 设置登录页面
                .loginProcessingUrl("/user/login")  // 自定义的登录接口
                .and()
                .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/login.html").permitAll()     // 设置所有人都可以访问登录页面
//                .anyRequest()               // 任何请求,登录后可以访问
//                .authenticated()
                .and()
                .csrf().disable();          // 关闭csrf防护
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
