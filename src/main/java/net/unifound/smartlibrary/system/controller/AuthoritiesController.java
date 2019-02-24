package net.unifound.smartlibrary.system.controller;

import net.unifound.smartlibrary.common.result.BaseResult;
import net.unifound.smartlibrary.common.result.Result;
import net.unifound.smartlibrary.common.result.Status;
import net.unifound.smartlibrary.common.utils.ReflectUtil;
import net.unifound.smartlibrary.system.base.BaseController;
import net.unifound.smartlibrary.system.model.Authorities;
import net.unifound.smartlibrary.system.model.User;
import net.unifound.smartlibrary.system.service.AuthoritiesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限管理
 */
@RestController
@RequestMapping("/system/authorities")
public class AuthoritiesController extends BaseController {
    @Autowired
    private AuthoritiesService authoritiesService;

    //查询所有权限集合
    @RequiresPermissions("authorities:view")
    @RequestMapping("/list")
    public BaseResult<Object> list(Integer roleId){
        List<Map<String,Object>> maps= new ArrayList<>();
        List<Authorities> authorities=authoritiesService.list();
        List<Authorities> roleAuths=authoritiesService.listByRoleId(roleId);
        for (Authorities authority : authorities) {
            Map<String,Object> map = ReflectUtil.objectToMap(authority);
            map.put("checked",0);
            for (Authorities roleAuth : roleAuths) {
                if (authority.getAuthorityId().equals(roleAuth.getAuthorityId())){
                    map.put("checked",1);
                    break;
                }
            }
            maps.add(map);
        }
        return Result.success(maps);
    }
    //添加权限
    @RequiresPermissions("authorities:add")
    @RequestMapping("/add")
    public BaseResult<Object> add(Authorities authorities){
        if (authoritiesService.add(authorities)){
            return Result.success();
        }else {
            return Result.error(Status.FAIL.getCode(),"添加失败");
        }
    }
    //修改权限
    @RequiresPermissions("authorities:edit")
    @PostMapping("/update")
    public BaseResult<Object> update(Authorities authorities){
        if (authoritiesService.update(authorities)){
            return Result.success();
        }else {
            return Result.error(Status.FAIL.getCode(),"修改成功");
        }
    }
    //删除权限
    @RequiresPermissions("authorities:delete")
    @RequestMapping("/delete")
    public BaseResult<Object> delete(Integer authorityId){
        if (authoritiesService.delete(authorityId)){
            return Result.success();
        }else {
            return Result.error(Status.FAIL.getCode(),"删除成功");
        }
    }
}
