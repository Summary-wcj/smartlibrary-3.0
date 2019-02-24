package net.unifound.smartlibrary.system.controller;

import net.unifound.smartlibrary.common.result.BaseResult;
import net.unifound.smartlibrary.common.result.Result;
import net.unifound.smartlibrary.common.result.Status;
import net.unifound.smartlibrary.common.utils.JSONUtil;
import net.unifound.smartlibrary.common.utils.StringUtil;
import net.unifound.smartlibrary.system.model.Authorities;
import net.unifound.smartlibrary.system.model.Role;
import net.unifound.smartlibrary.system.service.AuthoritiesService;
import net.unifound.smartlibrary.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthoritiesService authoritiesService;

    //查询角色列表
    @RequiresPermissions("role:view")
    @RequestMapping("/list")
    public BaseResult<Object> list(Integer page, Integer limit,String searchKey){
        List<Role> roles = roleService.list(page,limit,searchKey,false);
        return Result.success(roles);
    }
    //菜单列表（在权限添加和编辑的时候权限菜单选择的列表数据）
    @RequestMapping("/listMenu")
    public BaseResult<Object> listMenu(){
        List<Authorities> listMenu = authoritiesService.listMenu();
        return Result.success(listMenu);
    }

    //添加角色
    @RequiresPermissions("role:add")
    @RequestMapping("/add")
    public BaseResult<Object> add(Role role){
        if (roleService.add(role)){
            return Result.success();
        }else {
            return Result.error(Status.FAIL.getMessage());
        }
    }
    //修改角色
    @RequiresPermissions("role:edit")
    @RequestMapping("/update")
    public BaseResult<Object> update(Role role){
        if (roleService.update(role)){
            return Result.success();
        }else {
            return Result.error(Status.FAIL.getMessage());
        }
    }
    //删除角色（逻辑删除）
    @RequiresPermissions("role:delete")
    @PostMapping("/delete")
    public BaseResult<Object> delete(Integer roleId){
        if (roleService.updateState(roleId,1)){
            return Result.success();
        }else {
            return Result.error("删除失败");
        }
    }
    //角色权限树
    @GetMapping("/authTree")
    public BaseResult<Object> authTree(Integer roleId){
        List<Authorities> authorities = authoritiesService.list();
        List<Authorities> roleAuths=authoritiesService.listByRoleId(roleId);
        List<Map<String,Object>> authTrees = new ArrayList<>();
        for (Authorities authority : authorities) {
            Map<String,Object> authTree = new HashMap<>();
            authTree.put("id",authority.getAuthorityId());
            authTree.put("name",authority.getAuthorityName()+" "+ StringUtil.getStr(authority.getAuthority()));
            authTree.put("pId",authority.getParentId());
            authTree.put("open",true);
            authTree.put("checked",false);
            for (Authorities roleAuth : roleAuths) {
                if (authority.getAuthorityId().equals(roleAuth.getAuthorityId())){
                    authTree.put("checked",true);
                    break;
                }
            }
            authTrees.add(authTree);
        }
        return Result.success(authTrees);
    }
    //修改保存权限 (authIds 是json字符串)
    @RequiresPermissions("role:auth")
    @PostMapping("/updateRoleAuth")
    public BaseResult<Object> update(Integer roleId,String authIds){
        if (authoritiesService.updateRoleAuth(roleId, JSONUtil.parseArray(authIds, Integer.class))) {
            return Result.success();
        }
        return Result.error(Status.FAIL.getCode(),"修改失败");
    }
}
