package net.unifound.smartlibrary.system.service;

import com.baomidou.mybatisplus.service.IService;
import net.unifound.smartlibrary.common.exception.BusinessException;
import net.unifound.smartlibrary.common.result.PageResult;
import net.unifound.smartlibrary.system.model.User;


public interface UserService extends IService<User> {
    //根据用户名查询用户信息
    User getByUsername(String username);
    /**
     * 获取用户信息集合
     * @param pageNum 当前页
     * @param pageSize 页数
     * @param showDelete 是否禁用
     * @param searchKey 查询的key
     * @param searchValue 查询的value
     * @return
     */
    PageResult<User> list(int pageNum, int pageSize, boolean showDelete, String searchKey, String searchValue);
    //根据userId获取用户信息
    User getByUserId(Integer userId);
    //添加用户
    boolean add(User user) throws BusinessException;
    //更新用户
    boolean update(User user);
    //更新用户状态
    boolean updateState(Integer userId,Integer state);
    //重置密码
    boolean updatePassword(Integer userId,String username,String password);
    //删除用户（物理删除）
    boolean delete(Integer userId);
}
