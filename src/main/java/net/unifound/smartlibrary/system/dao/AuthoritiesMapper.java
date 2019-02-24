package net.unifound.smartlibrary.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import net.unifound.smartlibrary.system.model.Authorities;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AuthoritiesMapper extends BaseMapper<Authorities> {
    //根据userId获取权限集合
    List<Authorities> listByUserId(Integer userId);
    //根据roleId获取权限集合（批量操作）
    List<Authorities> listByRoleIds(List<Integer> roleIds);
    //根据roleId获取权限集合（单个操作）
    List<Authorities> listByRoleId(Integer roleId);


}
