package net.unifound.smartlibrary.system.controller;

import cn.hutool.core.util.StrUtil;
import com.wf.captcha.utils.CaptchaUtil;
import net.unifound.smartlibrary.common.exception.ParameterException;
import net.unifound.smartlibrary.common.result.BaseResult;
import net.unifound.smartlibrary.common.result.Result;
import net.unifound.smartlibrary.common.result.Status;
import net.unifound.smartlibrary.common.utils.StringUtil;
import net.unifound.smartlibrary.common.utils.UserAgentGetter;
import net.unifound.smartlibrary.system.base.BaseController;
import net.unifound.smartlibrary.system.model.Authorities;
import net.unifound.smartlibrary.system.model.LoginRecord;
import net.unifound.smartlibrary.system.service.AuthoritiesService;
import net.unifound.smartlibrary.system.service.LoginRecordService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController extends BaseController {
    @Autowired
    private LoginRecordService loginRecordService;
    @Autowired
    private AuthoritiesService authoritiesService;

    @PostMapping("/login")
    public BaseResult<Object> doLogin(String username, String password, String captcha, HttpServletRequest request){
        if (StrUtil.hasBlank(username,password)){
            throw new ParameterException(Status.PARAMETER_ERROR.getCode(),"参数不能为空");
        }
        if (!CaptchaUtil.ver(captcha, request)) {
            CaptchaUtil.clear(request);
            throw new ParameterException(Status.PARAMETER_ERROR.getCode(),"验证码错误");
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
            addLoginRecord(getLoginUserId(),request);
            return Result.success("登录成功");
        } catch (IncorrectCredentialsException ice) {
            return Result.error(Status.PASSWORD_ERROR.getCode(),Status.PASSWORD_ERROR.getMessage());
        } catch (UnknownAccountException uae) {
            return Result.error(Status.ACCOUNT_NOT_EXIST.getCode(),Status.ACCOUNT_NOT_EXIST.getMessage());
        } catch (LockedAccountException e) {
            return Result.error(Status.ACCOUNT_LOCKED.getCode(),Status.ACCOUNT_LOCKED.getMessage());
        } catch (ExcessiveAttemptsException eae) {
            return Result.error(Status.EXCESSIVE_ATTEMPTS.getCode(),Status.EXCESSIVE_ATTEMPTS.getMessage());
        }
    }

    @GetMapping("/getMenuTreeList")
    public BaseResult<Object>getMenuTreeList(){
        List<Authorities> authorities = authoritiesService.listByUserId(getLoginUserId());
        List<Map<String, Object>> list = getMenuTree(authorities, -1);
        return Result.success(list);
    }

    /**
     * 递归转化树形菜单
     */
    private List<Map<String, Object>> getMenuTree(List<Authorities> authorities, Integer parentId) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < authorities.size(); i++) {
            Authorities temp = authorities.get(i);
            if (temp.getIsMenu() == 0 && parentId == temp.getParentId()) {
                Map<String, Object> map = new HashMap<>();
                map.put("menuName", temp.getAuthorityName());
                map.put("menuIcon", temp.getMenuIcon());
                map.put("menuUrl", StringUtil.isBlank(temp.getMenuUrl()) ? "javascript:;" : temp.getMenuUrl());
                map.put("subMenus", getMenuTree(authorities, authorities.get(i).getAuthorityId()));
                list.add(map);
            }
        }
        return list;
    }




    //添加登录日志
    private void addLoginRecord(Integer userId,HttpServletRequest request){
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        //添加到登录日志
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUserId(userId);
        loginRecord.setOsName(agentGetter.getOS());
        loginRecord.setDevice(agentGetter.getDevice());
        loginRecord.setBrowserType(agentGetter.getBrowser());
        loginRecord.setIpAddress(agentGetter.getIpAddr());
        loginRecordService.add(loginRecord);
    }

    //退出
    @RequestMapping("/doLogout")
    public BaseResult<Object> doLogout(){
        SecurityUtils.getSubject().logout();
        return Result.success("退出成功");
    }
}
