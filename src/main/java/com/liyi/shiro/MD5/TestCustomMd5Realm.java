package com.liyi.shiro.MD5;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;

/**
 * @author liyi
 * @create 2021 -10 -17 -20:01
 */
public class TestCustomMd5Realm { // 测试：自定义MD5的Realm
    public static void main(String[] args) {
        // 1、创建安全管理器
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        /*
            2、设置Realm使用hash凭证匹配器
         */
        CustomMd5Realm realm = new CustomMd5Realm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");  // 告知算法
        matcher.setHashIterations(1024); // 告知散列次数
        realm.setCredentialsMatcher(matcher);
        defaultSecurityManager.setRealm(realm);

        // 3、全局安全工具类
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        // 4、通过安全工具类获取 subject主体
        Subject subject = SecurityUtils.getSubject();
        // 5、创建令牌 token
        UsernamePasswordToken token = new UsernamePasswordToken("yibao", "123456");
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

        // 授权 (在认证代码之后)
        if (subject.isAuthenticated()) {  // 如果认证通过
            // 1.1、基于 "角色" 进行授权
            boolean isAdmin = subject.hasRole("admin");  // 判断是否包含某个 "单角色"
            System.out.println("是否拥有admin权限： " + isAdmin);

            // 1.2 基于 "多角色"
            ArrayList<String> list = new ArrayList<>();
            list.add("admin");
            list.add("user");
            boolean isAdminAndUser = subject.hasAllRoles(list);
            System.out.println("是否同时拥有admin和user权限： " + isAdminAndUser);

            // 1.3 基于其中一个角色
            boolean[] isAny = subject.hasRoles(list);
            for (boolean is : isAny) {
                System.out.println("是否拥有其中一个权限： " + is);
            }

            // =======================================================================================

            // 2.1 基于资源，判断是否具有某一个资源权限
            System.out.println("是否具备以下资源： " + subject.isPermitted("user:update:01"));
            System.out.println("是否具备以下资源： " + subject.isPermitted("user:delete:*"));

            // 2.2 分别具有以下权限资源
            boolean[] permitted = subject.isPermitted("user:*:*", "user:update:01", "order:*:01");
            for (boolean b : permitted) {
                System.out.println("具备以下资源： " + b);
            }

            // 2.3 是否同时具备以下权限资源
            boolean permittedAll = subject.isPermittedAll("user:create:01", "user:delete:*");
            System.out.println("是否同时具备以下资源： " + permittedAll);

        }


    }
}
