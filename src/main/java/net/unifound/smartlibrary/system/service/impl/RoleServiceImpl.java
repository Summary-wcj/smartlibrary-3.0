package net.unifound.smartlibrary.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import net.unifound.smartlibrary.common.exception.ParameterException;
import net.unifound.smartlibrary.common.result.Status;
import net.unifound.smartlibrary.system.dao.RoleAuthoritiesMapper;
import net.unifound.smartlibrary.system.dao.RoleMapper;
import net.unifound.smartlibrary.system.model.Role;
import net.unifound.smartlibrary.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthoritiesMapper roleAuthoritiesMapper;

    @Override
    public List<Role> getByUserId(Integer userId) {
        List<Role> roles = roleMapper.selectByUserId(userId);
        return roles;
    }

    @Override
    public List<Role> list(Integer pageNum, Integer pageSize,String searchKey,boolean showDelete) {
        Wrapper wrapper = new EntityWrapper();
        if (!showDelete){
            wrapper.eq("is_delete",0);
        }
        wrapper.orderBy("create_time",true);
        Page<Role> rolePage =new Page<>(pageNum,pageSize);
        List<Role> list = roleMapper.selectPage(rolePage,wrapper);
        if (searchKey !=null && !searchKey.trim().isEmpty()){
            searchKey=searchKey.trim();
            Iterator<Role> iterator=list.iterator();
            while (iterator.hasNext()){
                Role next=iterator.next();
                boolean b = String.valueOf(next.getRoleId()).contains(searchKey) || next.getRoleName().contains(searchKey) || next.getComments().contains(searchKey);
                if (!b) {
                    iterator.remove();
                }
            }
        }
        return list;
    }

    @Override
    public Role getByRoleId(Integer roleId) {
        Role role = roleMapper.selectById(roleId);
        return role;
    }

    @Override
    public boolean add(Role role) {
        role.setCreateTime(new Date());
        boolean res = roleMapper.insert(role)>0;
        return res;
    }

    @Override
    public boolean update(Role role) {
        boolean res = roleMapper.updateById(role)>0;
        return res;
    }

    @Override
    public boolean updateState(Integer roleId, int isDelete) {
        if (isDelete !=0&&isDelete !=1){
            throw  new ParameterException(Status.PARAMETER_ERROR.getCode(),"isDelete的值需要在[0,1]");
        }
        Role role = new Role();
        role.setRoleId(roleId);
        role.setIsDelete(isDelete);
        boolean res = roleMapper.updateById(role)>0;
        if (res){
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("role_id",roleId);
            //删除改角色下的权限
            roleAuthoritiesMapper.delete(wrapper);
        }
        return res;
    }

    @Override
    public boolean delete(Integer roleId) {
        boolean res=roleMapper.deleteById(roleId) > 0;
        return res;
    }
}
