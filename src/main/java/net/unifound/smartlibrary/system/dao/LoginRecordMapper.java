package net.unifound.smartlibrary.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import net.unifound.smartlibrary.system.model.LoginRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRecordMapper extends BaseMapper<LoginRecord> {
    List<LoginRecord> list(@Param("page") Page<LoginRecord> page, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("account") String account);
}
