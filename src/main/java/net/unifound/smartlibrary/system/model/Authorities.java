package net.unifound.smartlibrary.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 权限表
 */
@Data
@TableName("sys_authorities")
public class Authorities {
    /**
     * 权限id
     */
    @TableId
    private Integer authorityId;
    /**
     * 权限名称
     */
    private String authorityName;
    /**
     * 权限标识（如果为空不会添加在shiro的权限列表中）
     */
    private String authority;
    /**
     * 菜单url
     */
    private String menuUrl;
    /**
     * 上级菜单
     */
    private Integer parentId;
    /**
     * 菜单还是按钮（菜单会显示在侧导航，按钮不会显示在侧导航，只要url不是空，都会作为权限标识）
     */
    private Integer isMenu;
    /**
     * 排序号
     */
    private Integer orderNumber;
    /**
     * 菜单图标
     */
    private String menuIcon;
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
}
