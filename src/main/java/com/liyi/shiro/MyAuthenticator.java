package com.liyi.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * 认证器
 *
 * @author liyi
 * @create 2021 -10 -16 -21:32
 */
public class MyAuthenticator {  // 认证器
    public static void main(String[] args) {
        // 1、创建安全管理器对象（使用子对象）
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 2、给安全器设置Realm（告诉安全管理器去哪里找验证信息）
        defaultSecurityManager.setRealm(new IniRealm("classpath:shiro.ini"));
        /*
            3.1、SecurityUtils 全局安全工具类
            3.2、告诉全局安全工具类使用那个安全管理器进行认证、授权
         */
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        // 4、关键对象 subject主体
        Subject subject = SecurityUtils.getSubject();
        // 5、创建令牌 token
        UsernamePasswordToken token = new UsernamePasswordToken("nanyi", "123456");
        // 6、登录认证（没有返回值，所以需要异常捕获处理）
        try {
            subject.login(token);
            System.out.println("当前认证状态为：" + subject.isAuthenticated());
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("异常：密码错误");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("异常：用户名错误");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
