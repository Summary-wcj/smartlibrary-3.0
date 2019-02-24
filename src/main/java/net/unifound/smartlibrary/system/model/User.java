package net.unifound.smartlibrary.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
/**
 * 用户表
 */
@Data
@TableName("sys_user")
public class User {
    /**
     * 主键id
     */
    @TableId
    private Integer userId;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private String sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 邮箱是否验证
     */
    private Integer emailVerified;
    /**
     * 人员id，关联person表，如果是教学系统，则关联学生表和教师表
     */
    private Integer personId;
    /**
     * 人员类型，比如：0学生，1教师
     */
    private Integer personType;
    /**
     * 用户状态，0正常，1锁定
     */
    private Integer state;
    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    /**
     * 角色
     */
    @TableField(exist = false)
    private List<Role> roles;
}
