/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(MyUserDetailsService.class);
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("用户的用户名: {}", username);

        String password = passwordEncoder.encode("123456");
        LOG.info("password: {}", password);

        // 参数分别是：用户名，密码，用户权限
        User user = new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
  }
}
