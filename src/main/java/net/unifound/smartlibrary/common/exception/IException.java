package net.unifound.smartlibrary.common.exception;

import lombok.Data;

/**
 * 自定义异常基类
 */
@Data
public abstract class IException extends RuntimeException {
    private static final long serialVersionUID = -1582874427218948396L;
    private Integer code;
    private Integer status;

    public IException() {
    }

    public IException(String message) {
        super(message);
    }

    public IException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public IException(Integer code,Integer status,String message){
        super(message);
        this.code=code;
        this.status=status;
    }
}
