package net.unifound.smartlibrary.common.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MybatisPlus配置
 */
@EnableTransactionManagement
@Configuration
@MapperScan("net.unifound.smartlibrary.*.dao")
public class MybatisPlusConfig {
    Log log = LogFactory.get();

    /**
     * 注入sql注入器
     */
    @Bean
    public ISqlInjector sqlInjector() {
        ISqlInjector sqlInjector=new LogicSqlInjector();
        log.info("sql注入器注入成功...");
        return sqlInjector;
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor=new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        log.info("mybatis分页插件注入成功...");
        return paginationInterceptor;
    }
}