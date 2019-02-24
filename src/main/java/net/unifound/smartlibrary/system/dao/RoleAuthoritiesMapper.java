package net.unifound.smartlibrary.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import net.unifound.smartlibrary.system.model.RoleAuthorities;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleAuthoritiesMapper extends BaseMapper<RoleAuthorities> {
    //添加角色所对应的权限
    int insertRoleAuths(@Param("roleId") Integer roleId, @Param("authIds") List<Integer> authIds);
}
