package net.unifound.smartlibrary.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 角色权限关联表
 */
@Data
@TableName("sys_role_authorities")
public class RoleAuthorities {
    /**
     * 主键，也可以用联合主键，根据个人习惯
     */
    @TableId
    private Integer id;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 权限id
     */
    private Integer authorityId;
    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
