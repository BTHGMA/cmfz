package com.baizhi.conf;

import com.baizhi.util.myRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroFilterConf {
    @Bean
    public ShiroFilterFactoryBean getshirofilterfactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        HashMap<String, String>map = new HashMap<>();
        map.put("/**","authc");
        map.put("/admin/login","anon");
        map.put("/jsp/main.jsp","anon");
        shiroFilterFactoryBean .setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/jsp/login.jsp");
        return shiroFilterFactoryBean;
    }
    @Bean
    public  SecurityManager getsecurityManager(myRealm myRealm, CacheManager cacheManager){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm);
        defaultWebSecurityManager.setCacheManager(cacheManager);
        return defaultWebSecurityManager;
    }
    @Bean
    public myRealm getmyRealm(CredentialsMatcher credentialsMatcher){
        myRealm myRealm = new myRealm();
        myRealm.setCredentialsMatcher(credentialsMatcher);
        return myRealm;
    }
    @Bean
    public CredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
    @Bean
    public CacheManager cacheManager(){
        return  new EhCacheManager();
    }
}
