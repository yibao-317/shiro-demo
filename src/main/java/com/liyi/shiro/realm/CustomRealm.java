package com.liyi.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义Realm：将.ini配置文件信息  -->> 数据库
 *
 * @author liyi
 * @create 2021 -10 -16 -22:20
 */
public class CustomRealm extends AuthorizingRealm { // 自定义Realm
    // 方法：授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 方法：认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1、在 token中获取用户名
        String principal = (String) token.getPrincipal();
        // 2、根据用户唯一信息，利用jdbc/mybatis查询数据库
        if ("yibao".equals(principal)) {
            /*
                3.1、将数据库中查询到的信息，封装进此对象
                3.2、参数一：真实的用户名（其实就是从token中得到的信息，本身就是根据这个信息去数据库查询）
                    参数二：查询到的密码
                    参数三：当前Realm的名字
             */
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,"111",this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }

}
