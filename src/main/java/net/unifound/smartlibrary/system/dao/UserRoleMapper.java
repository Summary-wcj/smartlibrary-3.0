package net.unifound.smartlibrary.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import net.unifound.smartlibrary.system.model.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {
    //根据id获取用户角色集合
    List<UserRole> selectByUserId(@Param("userIds") List<Integer> userIds);
    //插入该用户下的角色
    int insertBatch(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);
}
