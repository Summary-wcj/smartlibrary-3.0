package net.unifound.smartlibrary.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import net.unifound.smartlibrary.common.exception.BusinessException;
import net.unifound.smartlibrary.system.dao.AuthoritiesMapper;
import net.unifound.smartlibrary.system.dao.RoleAuthoritiesMapper;
import net.unifound.smartlibrary.system.model.Authorities;
import net.unifound.smartlibrary.system.model.RoleAuthorities;
import net.unifound.smartlibrary.system.service.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthoritiesServiceImpl  implements AuthoritiesService {
    @Autowired
    private AuthoritiesMapper authoritiesMapper;
    @Autowired
    private RoleAuthoritiesMapper roleAuthoritiesMapper;

    @Override
    public List<Authorities> listByUserId(Integer userId) {
        List<Authorities> authorities = authoritiesMapper.listByUserId(userId);
        return authorities;
    }

    @Override
    public List<Authorities> list() {
        Wrapper wrapper = new EntityWrapper<Authorities>();
        wrapper.orderBy("order_number",true);
        List list = authoritiesMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<Authorities> listMenu() {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("is_menu",0).orderBy("order_number",true);
        List list = authoritiesMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<Authorities> listByRoleIds(List<Integer> roleIds) {
        if (roleIds == null || roleIds.size() ==0){
            return new ArrayList<>();
        }
        List<Authorities> authorities = authoritiesMapper.listByRoleIds(roleIds);
        return authorities;
    }

    @Override
    public List<Authorities> listByRoleId(Integer roleId) {
        List<Authorities> list = authoritiesMapper.listByRoleId(roleId);
        return list;
    }

    @Override
    public boolean add(Authorities authorities) {
        authorities.setCreateTime(new Date());
        boolean res = authoritiesMapper.insert(authorities)>0;
        return res;
    }

    @Override
    public boolean update(Authorities authorities) {
        boolean res = authoritiesMapper.updateById(authorities)>0;
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(Integer authorityId) {
        //获取子节点
        Wrapper authoritiesWrapper = new EntityWrapper<Authorities>();
        authoritiesWrapper.eq("parent_id",authorityId);
        List<Authorities> childs = authoritiesMapper.selectList(authoritiesWrapper);
        if (childs!=null&&childs.size()>0){
            throw new BusinessException("请先删除子节点");
        }
        //删除authorityId对应的角色
        Wrapper roleAuthoritiesWrapper= new EntityWrapper<RoleAuthorities>();
        roleAuthoritiesWrapper.eq("authority_id",authorityId);
        roleAuthoritiesMapper.delete(roleAuthoritiesWrapper);
        //删除权限
        if (authoritiesMapper.deleteById(authorityId) <=0){
            throw new BusinessException("删除失败");
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoleAuth(Integer roleId, List<Integer> authIds) {
        Wrapper wrapper = new  EntityWrapper<RoleAuthorities>();
        wrapper.eq("role_id",roleId);
        boolean res = roleAuthoritiesMapper.delete(wrapper)>0;
        if (authIds !=null && authIds.size()>0){
            if (roleAuthoritiesMapper.insertRoleAuths(roleId,authIds)<authIds.size()){
                throw new BusinessException("操作失败");
            }
        }
        return true;
    }

}
