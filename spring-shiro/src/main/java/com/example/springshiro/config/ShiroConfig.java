package com.example.springshiro.config;

import com.example.springshiro.realm.AccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * step 3: 将DefaultWebSecurityManager对象注入ShiroFilterFactoryBean对象，并注入IOC容器
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        //权限设置
        Map<String,String> map = new HashMap<>();
        map.put("/index","anon");   //index主页任何人都可以访问
        map.put("/main","authc");   //main页面必须登录以后才能访问
        map.put("/manager","perms[manager]");   //manager页面需要有manager权限的人才能访问
        map.put("/administrator","roles[administrator]");  //administrator页面需要有administrator角色的人才能访问
        factoryBean.setFilterChainDefinitionMap(map);

        //设置login页面链接
        factoryBean.setLoginUrl("/login");

        //设置提示未授权页面链接
        factoryBean.setUnauthorizedUrl("/unauth");

        return factoryBean;
    }

    /**
     * step 2: 将AccountRealm对象注入DefaultWebSecurityManager对象，并将这个对象注入IOC容器
     * @param accountRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("accountRealm") AccountRealm accountRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);
        return securityManager;
    }

    /**
     * step 1: 创建一个AccountRealm对象，注入IOC容器
     * @return
     */
    @Bean
    public AccountRealm accountRealm(){
        return new AccountRealm();
    }
}
