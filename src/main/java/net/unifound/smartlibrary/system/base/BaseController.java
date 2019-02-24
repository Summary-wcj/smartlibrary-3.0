package net.unifound.smartlibrary.system.base;

import com.wf.captcha.utils.CaptchaUtil;
import net.unifound.smartlibrary.system.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller基类
 */
public class BaseController {
    /**
     * 获取当前登录用户信息
     * @return
     */
    public User getLoginUser(){
        Subject subject = SecurityUtils.getSubject();
        if (subject !=null){
            Object principal = subject.getPrincipal();
            if (principal != null){
                return (User) principal;
            }
        }
        return null;
    }

    /**
     * 获取当前用户登录的userId
     * @return
     */
    public Integer getLoginUserId(){
        User user = getLoginUser();
        return user == null ? null : user.getUserId();
    }
    /**
     * 获取当前用户登录的username
     * @return
     */
    public String  getLoginUsername(){
        User user = getLoginUser();
        return user == null ? null : user.getUsername();
    }

    /**
     * 获取验证码
     * @param request
     * @param response
     */
    @RequestMapping("/assets/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        try {
            CaptchaUtil.out(4,request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
