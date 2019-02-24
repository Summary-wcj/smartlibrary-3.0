package net.unifound.smartlibrary.common.result;


public class Result {
    /**
     * 返回成功
     * @param data 返回结果
     * @param <T> 类型
     * @return
     */
    public static <T> BaseResult<T> success(T data) {
        return commonResult(0, Status.SUCCESS.getCode(),Status.SUCCESS.getMessage(),data);
    }
    public static <T> BaseResult<T> success(String message){
        return commonResult(0,Status.SUCCESS.getCode(),message);
    }
    /**
     * 返回成功
     * @param <T> 类型
     * @return
     */
    public static <T> BaseResult<T> success(){
        return commonResult(0, Status.SUCCESS.getCode(),Status.SUCCESS.getMessage());
    }

    public static <T> BaseResult<T>  error(String errorMessage) {
        return error(Status.FAIL.getCode(),errorMessage);
    }


    public static <T> BaseResult<T> error(Integer code, String errorMessage) {
        return commonResult(1, code, errorMessage);
    }



    private static <T> BaseResult<T> commonResult(Integer status,Integer code,String message,T data){
        BaseResult<T> result = new BaseResult<>();
        result.setStatus(status);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    private static <T> BaseResult<T> commonResult(Integer status,Integer code,String message){
        BaseResult<T> result = new BaseResult<>();
        result.setStatus(status);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
