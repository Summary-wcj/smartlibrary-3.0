package net.unifound.smartlibrary.common.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import net.unifound.smartlibrary.common.result.BaseResult;
import net.unifound.smartlibrary.common.result.Result;
import net.unifound.smartlibrary.common.result.Status;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    Log log = LogFactory.get();

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public BaseResult<Object> errorHandler(Exception e) {
        Map<String,Object> map =new HashMap<>();
        //根据不同错误获取不同的错误信息
        if (e instanceof UnauthorizedException){
            return  Result.error(Status.PERMISSION_DENIED.getCode(),Status.PERMISSION_DENIED.getMessage());
        }else if (e instanceof NoHandlerFoundException){
            return Result.error(Status.RESOURCE_NOT_EXIST.getCode(),Status.RESOURCE_NOT_EXIST.getMessage());
        }else if (e instanceof ParameterException){
            return Result.error(((ParameterException) e).getCode(),e.getMessage());
        }else if(e instanceof BusinessException){
            return Result.error(((BusinessException) e).getCode(),e.getMessage());
        }
        else {
            String message = e.getMessage();
            return  Result.error(Status.FAIL.getCode(),message);
        }
    }
}
