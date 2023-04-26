package com.team033.teamup.util;

public class Result<T> {
    //返回信息
    private String msg;
    //数据是否正常请求      USE boolean or string code?
    private boolean success;
    //具体返回的数据
    private T detail;
    /*
        status code
        200 - OK Success
        201 - Created Success, something was created
        202 - Accepted but still processing
        400 - Bad Request, invalid data
        401 - Unauthorized, do not have permissions
        500 - Internal Server Error
     */
    private int code;

    public Result() {
        this.msg = null;
        this.success = false;
        this.detail = null;
        this.code = -1;
    }

    public Result(T detail) {
        this.detail = detail;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setMsg("success!");
        return result;
    }

    public static <T> Result<T> success(T detail) {
        Result<T> result = new Result<>(detail);
        result.setSuccess(true);
        result.setMsg("success!");
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> success(T detail, String msg) {
        Result<T> result = new Result<>(detail);
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    public static Result error(boolean status, String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }
}
