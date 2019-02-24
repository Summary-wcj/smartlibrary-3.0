package net.unifound.smartlibrary.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
/**
 * 角色表
 */
@Data
@TableName("sys_role")
public class Role {
    /**
     * 角色id
     */
    @TableId
    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 描述
     */
    private String comments;
    /**
     * 逻辑删除，0未删除，1已删除
     */
    private Integer isDelete;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    public Role(){
    }

    public Role(Integer roleId,String roleName){
        setRoleId(roleId);
        setRoleName(roleName);
    }
    public Role(Integer roleId){
        setRoleId(roleId);
    }
}
