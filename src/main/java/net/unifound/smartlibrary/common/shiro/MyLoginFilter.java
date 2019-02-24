package net.unifound.smartlibrary.common.shiro;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import net.unifound.smartlibrary.common.result.BaseResult;
import net.unifound.smartlibrary.common.result.Result;
import net.unifound.smartlibrary.common.result.Status;
import net.unifound.smartlibrary.common.utils.JSONUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * 自定义shiro过滤器
 * 判断登录过滤器
 */
public class MyLoginFilter extends AccessControlFilter {
    Log log= LogFactory.get();

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.info("进入过滤器............");
        Subject subject = getSubject(servletRequest, servletResponse);
        //获取session设置失效时间
        Session session = subject.getSession();
        session.setTimeout(3600000);
        System.out.println("当前sessionId："+subject.getSession().getId());
        //判断是否登录
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            if (isAjax((HttpServletRequest) servletRequest)) {
                log.info("this is ajax request..........");
                servletResponse.setContentType("application/json;charset=UTF-8");
                PrintWriter out = servletResponse.getWriter();
                BaseResult<Object> res=Result.error(Status.LOGIN_EXPIRED.getCode(),Status.LOGIN_EXPIRED.getMessage());
                String json = JSONUtil.parseJsonStr(res);
                out.write(json);
                out.flush();
                return false;
            }else {
                servletResponse.setContentType("application/json;charset=UTF-8");
                PrintWriter out = servletResponse.getWriter();
                BaseResult<Object> res=Result.error(Status.LOGIN_EXPIRED.getCode(),Status.LOGIN_EXPIRED.getMessage());
                String json = JSONUtil.parseJsonStr(res);
                out.write(json);
                out.flush();
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是ajax请求
     */
    private boolean isAjax(HttpServletRequest request) {
        String xHeader = request.getHeader("X-Requested-With");
        if (xHeader != null && xHeader.contains("XMLHttpRequest")) {
            return true;
        }
        return false;
    }
}
