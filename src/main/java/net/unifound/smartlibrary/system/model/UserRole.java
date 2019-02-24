package net.unifound.smartlibrary.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户角色关联表
 * 如果你的用户只对应一个角色，把前台的多选select改成单选即可，不需要改表结构
 */
@Data
@TableName("sys_user_role")
public class UserRole {
    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roleName;
}
