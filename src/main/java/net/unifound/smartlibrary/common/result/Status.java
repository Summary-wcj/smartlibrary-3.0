package net.unifound.smartlibrary.common.result;

/**
 * 异常信息枚举类
 */
public enum Status {
    SUCCESS(200,"操作成功"),
    PARAMETER_ERROR(400,"Bad Request"),
    LOGIN_EXPIRED(401,"登录过期，请重新登录"),
    FAIL(500,"内部错误"),
    PERMISSION_DENIED(403,"没有权限"),
    RESOURCE_NOT_EXIST(404,"资源不存在"),
    ACCOUNT_NOT_EXIST(4002,"账号不存在"),
    PASSWORD_ERROR(4004,"密码错误"),
    ACCOUNT_LOCKED(4005,"账号被锁定"),
    EXCESSIVE_ATTEMPTS(4006,"操作频繁，请稍后再试"),
    USER_EXIST(4007,"用户已存在")
    ;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String message;


    Status(Integer code,String message){
        this.setCode(code);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
