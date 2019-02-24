package net.unifound.smartlibrary.system.controller;

import cn.hutool.core.util.StrUtil;
import net.unifound.smartlibrary.common.result.BaseResult;
import net.unifound.smartlibrary.common.result.PageResult;
import net.unifound.smartlibrary.common.result.Result;
import net.unifound.smartlibrary.system.model.LoginRecord;
import net.unifound.smartlibrary.system.service.LoginRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/loginRecord")
public class LoginRecordController {
    @Autowired
    private LoginRecordService loginRecordService;

    //查询所有登录日志集合
    @RequiresPermissions("loginRecord:view")
    @RequestMapping("/list")
    public BaseResult<Object> list(int page,int limit,String startDate,String endDate,String account){
        if (StrUtil.hasBlank(startDate)){
            startDate=null;
        }else{
            startDate +=" 00:00:00";
        }
        if (StrUtil.hasBlank(endDate)){
            endDate=null;
        }else{
            endDate +=" 23:59:59";
        }
        if (StrUtil.hasBlank(account)){
            account=null;
        }
        PageResult<LoginRecord> list = loginRecordService.list(page, limit, startDate, endDate, account);
        return Result.success(list);
    }

}
