package net.unifound.smartlibrary.system.controller;

import net.unifound.smartlibrary.common.exception.ParameterException;
import net.unifound.smartlibrary.common.result.*;
import net.unifound.smartlibrary.common.utils.EndecryptUtil;
import net.unifound.smartlibrary.system.base.BaseController;
import net.unifound.smartlibrary.system.model.Role;
import net.unifound.smartlibrary.system.model.User;
import net.unifound.smartlibrary.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    //查询用户列表
    @RequiresPermissions("user:view")
    @RequestMapping("/list")
    public BaseResult<PageResult<User>> list(Integer page, Integer limit, String searchKey, String searchValue){
        PageResult<User> list = userService.list(page, limit, true, searchKey, searchValue);
        return Result.success(list);
    }
    /**
     * 添加用户
     * @param user 用户信息对象
     * @param roleId 角色id（）
     * @return
     */
    @RequiresPermissions("user:add")
    @PostMapping("/add")
    public BaseResult<Object> add(User user, String roleId){
        user.setRoles(getRoles(roleId));
        user.setPassword("123456");
        if (userService.add(user)){
            return Result.success();
        }else {
            return Result.error(Status.FAIL.getCode(),Status.FAIL.getMessage());
        }
    }
    //修改用户
    @RequiresPermissions("user:edit")
    @RequestMapping("/update")
    public BaseResult<Object> update(User user, String roleId){
        user.setRoles(getRoles(roleId));
        if (userService.update(user)){
            return Result.success();
        }else {
            return Result.error("修改失败");
        }
    }
    //更新用户状态
    @RequiresPermissions("user:delete")
    @RequestMapping("/updateState")
    public BaseResult<Object> updateState(Integer userId, Integer state){
        if (userService.updateState(userId,state)){
            return Result.success();
        }else {
            return Result.error("修改失败");
        }
    }

    //重置密码
    @RequiresPermissions("user:edit")
    @RequestMapping("/resetPassword")
    public BaseResult<Object> resetPassword(Integer userId){
        User user = userService.getByUserId(userId);
        if (userService.updatePassword(userId,user.getUsername(),"123456")){
            return Result.success();
        }else {
            return Result.error("操作失败");
        }
    }
    //修改密码
    @RequestMapping("/updatePassword")
    public BaseResult<Object> updatePassword(String oldPwd,String newPwd){
        //验证原密码是否正确
        String oldPwsSecret= EndecryptUtil.encrytMd5(oldPwd,getLoginUsername(),3);
        if (!oldPwsSecret.equals(getLoginUser().getPassword())){
            return Result.error(Status.PASSWORD_ERROR.getCode(),"原密码输入不正确");
        }
        if (userService.updatePassword(getLoginUserId(),getLoginUsername(),newPwd)){
            return Result.success("修改成功");
        }else {
            return Result.error("修改失败");
        }
    }
    /**
     * 获取角色集合
     * @param roleStr 角色（多个角色使用逗号拼接的字符串）
     * @return
     */
    private List<Role> getRoles(String roleStr){
        List<Role> roles=new ArrayList<>();
        if (roleStr == null){
            throw new ParameterException(Status.PASSWORD_ERROR.getCode(),"参数不能为空");
        }
        String[] split=roleStr.split(",");
        for (String s : split) {
            if (s.equals("1")){
                throw new ParameterException(Status.PARAMETER_ERROR.getCode(),"不能添加超级管理员");
            }
            roles.add(new Role(Integer.parseInt(s)));
        }
        return roles;
    }
}
