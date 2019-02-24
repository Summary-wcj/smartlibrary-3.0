package net.unifound.smartlibrary.system.service;

import net.unifound.smartlibrary.system.model.Authorities;

import java.util.List;

public interface AuthoritiesService {
    //根据userId获取权限集合
    List<Authorities> listByUserId(Integer userId);
    //获取权限集合
    List<Authorities> list();
    //菜单集合
    List<Authorities> listMenu();
    //根据roleId获取权限集合（批量）
    List<Authorities> listByRoleIds(List<Integer> roleIds);
    //根据roleId获取权限集合(单个)
    List<Authorities> listByRoleId(Integer roleId);
    //添加权限
    boolean add(Authorities authorities);
    //修改权限
    boolean update(Authorities authorities);
    //删除权限
    boolean delete(Integer authorityId);
    //修改保存权限树
    boolean updateRoleAuth(Integer roleId,List<Integer> authIds);
}
