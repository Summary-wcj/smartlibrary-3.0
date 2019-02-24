package net.unifound.smartlibrary.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ProjectName: smartlib-school-api
 * @Package: net.unifound.smartlib.school.config
 * @ClassName: SecurityPermitAllConfig
 * @Description:
 * @Author: localAccount
 * @Date: 2019/2/20 10:14
 * @修改人:
 * @修改日期:
 * @修改原因:
 * @Version: 2.1
 */
@Configuration
public class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll()
                .and().csrf().disable();
    }
}