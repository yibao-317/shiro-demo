package com.liyi.shiro.MD5;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @author liyi
 * @create 2021 -10 -17 -20:00
 */
public class CustomMd5Realm extends AuthorizingRealm { // 自定义MD5Realm

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 1、获取用户主信息
        String principal = (String) principals.getPrimaryPrincipal();
        /*
            2、根据用户，查询数据库，该用户拥有的权限，封装进权限对象
         */
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 2.1 方式一：查询出角色信息 --- 添加角色
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("user");

        // 2.2 方式二：查询出资源信息 --- 添加资源
        simpleAuthorizationInfo.addStringPermission("user:create:*");
        simpleAuthorizationInfo.addStringPermission("user:update:01");

        return simpleAuthorizationInfo;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1、在token中获取用户名
        String principal = (String) token.getPrincipal();
        // 2、根据用户名，通过JDBC/Mybatis等，查询信息
        if ("yibao".equals(principal)) {
            SimpleAuthenticationInfo simpleAuthenticationInfo =
                    new SimpleAuthenticationInfo(principal,
                            "a15bb908d7871b1b588350c47862f99e", // 这个是123456通过MD5算法后，在数据库中存储取出来的
                            ByteSource.Util.bytes("sdifh"), // 这个参数是 "盐"
                            this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
