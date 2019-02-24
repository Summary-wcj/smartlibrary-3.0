package net.unifound.smartlibrary.system.service;

import net.unifound.smartlibrary.system.model.Role;

import java.util.List;

public interface RoleService {

    //根据userId获取用户角色集合
    List<Role> getByUserId(Integer userId);
    //获取角色集合
    List<Role> list(Integer pageNum, Integer pageSize,String searchKey, boolean showDelete);
    //根据roleId获取角色信息
    Role getByRoleId(Integer roleId);
    //添加角色
    boolean add(Role role);
    //修改角色
    boolean update(Role role);
    //删除角色(逻辑删除)
    boolean updateState(Integer roleId,int isDelete);
    //删除角色（物理删除）
    boolean delete(Integer roleId);
}
