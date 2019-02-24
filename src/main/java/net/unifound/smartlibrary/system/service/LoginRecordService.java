package net.unifound.smartlibrary.system.service;

import net.unifound.smartlibrary.common.result.PageResult;
import net.unifound.smartlibrary.system.model.LoginRecord;

public interface LoginRecordService {
    //添加登录日志
    boolean add(LoginRecord loginRecord);
    //查询所有登录日志记录
    PageResult<LoginRecord> list(int pageNum,int pageSize,String startDate,String endDate,String account);
}
