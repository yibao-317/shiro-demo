package com.liyi.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * @author liyi
 * @create 2021 -10 -16 -22:23
 */
public class TestCustomRealm { // 测试自定义Realm
    public static void main(String[] args) {
        // 1、创建安全管理器
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 2、设置Realm（自定义的）
        defaultSecurityManager.setRealm(new CustomRealm());
        // 3、全局安全工具类
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        // 4、通过安全工具类获取 subject主体
        Subject subject = SecurityUtils.getSubject();
        // 5、创建令牌 token
        UsernamePasswordToken token = new UsernamePasswordToken("yibao", "111");
        // 6、登录认证
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
