package net.unifound.smartlibrary.common.result;

import lombok.Data;

@Data
public class BaseResult<T> {
    /**
     * 服务器返回的状态码
     */
    private Integer code;
    /**
     * 0 : 处理成功， 1 : 处理失败
     */
    private Integer status;
    /**
     * 服务器的错误信息
     */
    private  String message;
    /**
     * 服务器返回的数据
     */
    private T data;
}
