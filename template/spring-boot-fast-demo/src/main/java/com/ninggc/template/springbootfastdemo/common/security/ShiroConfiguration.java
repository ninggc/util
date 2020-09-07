package com.ninggc.template.springbootfastdemo.common.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public class ShiroConfiguration {
    @Bean
    public Realm realm() {
        return new AbstractUserRealm();
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // logged in users with the 'admin' role
        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // logged in users with the 'document:read' permission
        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

        // all other paths require a logged in user
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

    @Component
    static class AbstractUserRealm extends com.ninggc.template.springbootfastdemo.common.security.AbstractUserRealm {

        @Override
        protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
            String currentLoginName = (String) principals.getPrimaryPrincipal();
            return super.getAuthenticationCacheKey(principals);
        }
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            // String currentLoginName = (String) au.getPrimaryPrincipal();

            return null;
        }
    }
}
