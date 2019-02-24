package net.unifound.smartlibrary.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import net.unifound.smartlibrary.common.exception.BusinessException;
import net.unifound.smartlibrary.common.exception.ParameterException;
import net.unifound.smartlibrary.common.result.PageResult;
import net.unifound.smartlibrary.common.result.Status;
import net.unifound.smartlibrary.common.utils.EndecryptUtil;
import net.unifound.smartlibrary.system.dao.UserMapper;
import net.unifound.smartlibrary.system.dao.UserRoleMapper;
import net.unifound.smartlibrary.system.model.Role;
import net.unifound.smartlibrary.system.model.User;
import net.unifound.smartlibrary.system.model.UserRole;
import net.unifound.smartlibrary.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    Log log= LogFactory.get();
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User getByUsername(String username) {
        User user = baseMapper.selectByUsername(username);
        return user;
    }


    @Override
    public PageResult<User> list(int pageNum, int pageSize, boolean showDelete, String searchKey, String searchValue) {
        Wrapper<User> wrapper = new EntityWrapper<>();
        if (!StrUtil.hasBlank(searchKey)){
            wrapper.like(searchKey,searchValue);
        }
        //不限显示锁定用户
        if (!showDelete){
            wrapper.eq("state",0);
        }
        wrapper.orderBy("create_time",true);
        Page<User> userPage =new Page<>(pageNum,pageSize);
        List<User> userList = baseMapper.selectPage(userPage,wrapper);

        if (userList != null && userList.size()>0){
            //查询获取user的角色信息集合
            List<UserRole> userRoleList=userRoleMapper.selectByUserId(getUserIds(userList));
            for (User user : userList) {
                List<Role> roles = new ArrayList<>();
                for (UserRole userRole : userRoleList) {
                    if (user.getUserId().equals(userRole.getUserId())){
                        roles.add(new Role(userRole.getRoleId(),userRole.getRoleName()));
                    }
                }
                user.setRoles(roles);
            }
        }
        System.out.println("总条数："+userPage.getTotal());
        System.out.println("当前页："+userPage.getCurrent());
        System.out.println("总页码："+userPage.getPages());
        System.out.println("每页多少条："+userPage.getSize());
        System.out.println("是否有上一页："+userPage.hasPrevious());
        System.out.println("是否有下一页："+userPage.hasNext());
        System.out.println(userList);
        PageResult<User> data =new PageResult<>(userPage,userList);
        return data;
    }

    @Override
    public User getByUserId(Integer userId) {
        User user = baseMapper.selectById(userId);
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(User user) throws BusinessException {
        if (baseMapper.selectByUsername(user.getUsername()) !=null){
            throw new BusinessException(Status.USER_EXIST.getCode(),Status.USER_EXIST.getMessage());
        }
        user.setPassword(EndecryptUtil.encrytMd5(user.getPassword(),user.getUsername(),3));
        user.setState(0);
        user.setCreateTime(new Date());
        boolean res=baseMapper.insert(user)>0;
        if (res){
            List<Integer> roleIds=getRoleIds(user.getRoles());
            if (userRoleMapper.insertBatch(user.getUserId(),roleIds) < roleIds.size()){
                throw new BusinessException("添加失败");
            }
        }
        return res;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(User user) {

        //设置账号不能修改
        user.setUsername(null);
        boolean res = baseMapper.updateById(user)>0;
        if (res){
            //删除该用户下的所有角色
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("user_id",user.getUserId());
            userRoleMapper.delete(wrapper);
            //再插入修改有的新的角色
            List<Integer> roleIds=getRoleIds(user.getRoles());
            if (userRoleMapper.insertBatch(user.getUserId(),roleIds)<roleIds.size()){
                throw  new BusinessException("修改失败");
            }
        }
        return res;
    }

    @Override
    public boolean updateState(Integer userId, Integer state) {
        if (state !=0 && state !=1){
            throw new ParameterException(Status.PARAMETER_ERROR.getCode(),"state值需要在[0,1]中");
        }
        User user = new User();
        user.setUserId(userId);
        user.setState(state);
        boolean res = baseMapper.updateById(user)>0;
        return res;
    }

    @Override
    public boolean updatePassword(Integer userId, String username, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(EndecryptUtil.encrytMd5(password,username,3));
        boolean res = baseMapper.updateById(user)>0;
        return res;
    }

    @Override
    public boolean delete(Integer userId) {
        boolean res = baseMapper.deleteById(userId)>0;
        return res;
    }


    /**
     * 获取userId集合
     * @param userList 用户信息集合
     * @return 用户的userId集合
     */
    private List<Integer> getUserIds(List<User> userList){
        List<Integer> userIds= new ArrayList<>();
        for (User user : userList) {
            userIds.add(user.getUserId());
        }
        return userIds;
    }

    /**
     * 获取roleId集合
     * @param roles 用户角色集合
     * @return
     */
    private List<Integer> getRoleIds(List<Role> roles){
        List<Integer> roleIds = new ArrayList<>();
        if (roles != null&&roles.size()>0){
            for (Role role : roles) {
                roleIds.add(role.getRoleId());
            }
        }
        return roleIds;
    }
}
