package net.unifound.smartlibrary.common.shiro;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import net.unifound.smartlibrary.system.model.Authorities;
import net.unifound.smartlibrary.system.model.Role;
import net.unifound.smartlibrary.system.model.User;
import net.unifound.smartlibrary.system.service.AuthoritiesService;
import net.unifound.smartlibrary.system.service.RoleService;
import net.unifound.smartlibrary.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * shiro认证与授权
 */
public class UserRealm extends AuthorizingRealm {
    Log log= LogFactory.get();
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    private RoleService roleService;
    @Autowired
    @Lazy
    private AuthoritiesService authoritiesService;

    /**
     * 获取身份验证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("开始验证身份信息....");
        String username= (String) authenticationToken.getPrincipal();
        User user = userService.getByUsername(username);
        if (user == null){
            //账号不存在
            throw new UnknownAccountException();
        }
        if (user.getState() !=0){
            //账号被锁定
            throw new LockedAccountException();
        }
        //使用账号作为盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
        //与数据库中用户名和密码进行比对（第一个参数：认证实体类;第二个参数：数据库密码;第三个参数：盐值;第四个参数：当前realm对象的name）
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user,user.getPassword(), credentialsSalt,getName());
        log.info("身份验证结束....");
        return authenticationInfo;
    }

    /**
     * 获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("开始验证授权信息....");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取角色
        List<Role> userRoles =roleService.getByUserId(user.getUserId());
        Set<String> roles = new HashSet<>();
        for (Role userRole : userRoles) {
            roles.add(Convert.toStr(userRole.getRoleId()));
        }
        authorizationInfo.setRoles(roles);
        //获取权限
        List<Authorities> authorities = authoritiesService.listByUserId(user.getUserId());
        Set<String> permissions = new HashSet<>();
        for (Authorities authority : authorities) {
            if (!StrUtil.hasBlank(authority.getAuthority())){
                permissions.add(authority.getAuthority());
            }
        }
        authorizationInfo.setStringPermissions(permissions);
        log.info("授权验证结束....");
        log.info("当前用户："+user.getUsername()+"--拥有的权限："+permissions);
        return authorizationInfo;
    }
}
