package com.example.springshiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springshiro.entity.Account;
import com.example.springshiro.service.AccountService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class AccountRealm extends AuthorizingRealm {

    //注入service层，以便于后续从数据库中根据用户输入的用户名查找记录
    @Autowired
    private AccountService accountService;

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录用户信息
        Subject subject = SecurityUtils.getSubject();
        Account account = (Account)subject.getPrincipal(); //从subject中获取的是一个object对象，需要对其进行cast

        //设置角色
        Set<String> roles = new HashSet<>();
        roles.add(account.getRole());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        //设置权限
        info.addStringPermission(account.getPerms());

        return info;
    }

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //从数据库中查询出用户名与token中的用户名相同的记录。
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",token.getUsername());
        Account account = accountService.getOne(queryWrapper);

        //当数据库中有对应用户名的记录时，将记录中的密码与token中的密码进行比较，返回一个SimpleAuthenticationInfo，如果不存在记录，则返回null，shiro会抛出一个异常
        if(null != account){
            return new SimpleAuthenticationInfo(account,account.getPassword(),getName());
        }
        return null;
    }
}
