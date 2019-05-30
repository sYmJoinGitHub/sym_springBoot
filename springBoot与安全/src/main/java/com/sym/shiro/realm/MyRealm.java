package com.sym.shiro.realm;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
/**
 * 使用shiro必须要自定义 认证和授权 的逻辑
 *
 * @Auther: shenym
 * @Date: 2018-12-05 11:02
 */
public class MyRealm extends AuthorizingRealm{

    /**
     * 认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        /* 模拟数据 */
        String username = "张三";
        String password = "123456";
        /* 将需要校验的用户名和密码返回给shiro，它会自己拿authenticationToken里面的用户名和密码去验证 */
        return new SimpleAuthenticationInfo(username,password,super.getName());
    }


    /**
     * 授权逻辑
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }



}
