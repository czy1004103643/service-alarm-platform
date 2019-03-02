package com.newegg.ec.tool.entity;

import java.util.StringJoiner;

/**
 * @author Jay.H.Zou
 * @date 2019/2/10
 */
public class Result {

    private int code;

    private String message;

    private Object data;

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public Result(ResultCode resultCode, Object data) {
        this(resultCode);
        this.data = data;
    }

    public static Result successResult(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result successResult() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result failResult(Object data) {
        return new Result(ResultCode.FAIL, data);
    }

    public static Result failResult() {
        return new Result(ResultCode.FAIL);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", Result.class.getSimpleName() + "[", "]")
                .add("code=" + code)
                .add("message='" + message + "'")
                .add("data=" + data)
                .toString();
    }

    /**
     * 状态码
     */
    public enum ResultCode {

        /**
         * success
         */
        SUCCESS(0, "success"),
        /**
         * error
         */
        FAIL(-1, "error");

        private int code;

        private String message;

        ResultCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
