package net.unifound.smartlibrary.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import net.unifound.smartlibrary.system.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
    //根据用户名获取用户信息
    User selectByUsername(String username);
}
