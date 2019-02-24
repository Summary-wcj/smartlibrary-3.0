package net.unifound.smartlibrary.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import net.unifound.smartlibrary.common.result.PageResult;
import net.unifound.smartlibrary.system.dao.LoginRecordMapper;
import net.unifound.smartlibrary.system.model.LoginRecord;
import net.unifound.smartlibrary.system.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoginRecordImpl implements LoginRecordService {
    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Override
    public boolean add(LoginRecord loginRecord) {
        loginRecord.setCreateTime(new Date());
        boolean res = loginRecordMapper.insert(loginRecord)>0;
        return res;
    }

    @Override
    public PageResult<LoginRecord> list(int pageNum, int pageSize, String startDate, String endDate, String account) {
        Page<LoginRecord> loginRecordPage = new Page<>(pageNum,pageSize);
        List<LoginRecord> list = loginRecordMapper.list(loginRecordPage, startDate, endDate, account);
        return new PageResult<>(loginRecordPage,list);
    }
}
