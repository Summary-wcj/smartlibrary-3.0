package net.unifound.smartlibrary.common.shiro;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    Log log = LogFactory.get();

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //登录配置
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/");
        shiroFilter.setUnauthorizedUrl("/error?code=403");

        //自定义过滤器规则
        Map<String, Filter> filtersMap=new LinkedHashMap();
        filtersMap.put("myLoginFilter", new MyLoginFilter());
        shiroFilter.setFilters(filtersMap);

        //拦截配置
        Map<String,String> filterChainDefinitions =new LinkedHashMap<>();
        filterChainDefinitions.put("/assets/**", "anon");
        filterChainDefinitions.put("/module/**", "anon");
        filterChainDefinitions.put("/api/**", "anon");
        filterChainDefinitions.put("/druid/**", "anon");
        filterChainDefinitions.put("/login", "anon");
        filterChainDefinitions.put("/doLogin", "anon");
        //放过监听信息地址
        filterChainDefinitions.put("/actuator/**", "anon");
        filterChainDefinitions.put("/instances/**", "anon");
        //filterChainDefinitions.put("/logout", "logout");
        filterChainDefinitions.put("/**", "myLoginFilter,authc");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitions);

        log.info("shiro过滤器注入成功...");
        return shiroFilter;
    }


    @Bean("userRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public UserRealm userRealm(){
        UserRealm realm = new UserRealm();
        realm.setCredentialsMatcher(credentialsMatcher());
        log.info("身份认证器注入成功...");
        return realm;
    }
    /**
     * 默认web安全管理器
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        //securityManager.setCacheManager(cacheManager());
        log.info("默认web安全管理器注入成功...");
        return securityManager;
    }
    /**
     * 缓存管理器
     * @return
     */
    @Bean(name = "cacheManager")
    public EhCacheManager cacheManager(){
        EhCacheManager cacheManager =new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro/ehcache-shiro.xml");
        log.info("缓存管理器注入成功...");
        return cacheManager;
    }
    /**
     * 凭证匹配器
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //散列算法
        credentialsMatcher.setHashAlgorithmName("md5");
        //散列次数
        credentialsMatcher.setHashIterations(3);
        log.info("凭证匹配器注入成功...");
        return credentialsMatcher;
    }
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor=new LifecycleBeanPostProcessor();
        log.info("bean生命周期管理器注入成功...");
        return lifecycleBeanPostProcessor;
    }
    /**
     * shiro里实现的Advisor类,用来拦截注解的方法 .
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}
