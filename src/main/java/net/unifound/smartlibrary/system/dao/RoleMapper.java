package net.unifound.smartlibrary.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import net.unifound.smartlibrary.system.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {
    //根据userId获取角色集合
    List<Role> selectByUserId(Integer userId);
}
