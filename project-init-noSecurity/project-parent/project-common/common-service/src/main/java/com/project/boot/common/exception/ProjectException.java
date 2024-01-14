package com.project.boot.common.exception;

import com.project.boot.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class ProjectException extends RuntimeException {
    private Integer code ;          // 错误状态码
    private String message ;        // 错误消息

    private ResultCodeEnum resultCodeEnum ;     // 封装错误状态码和错误消息

    public ProjectException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum ;
        this.code = resultCodeEnum.getCode() ;
        this.message = resultCodeEnum.getMessage();
    }

    public ProjectException(Integer code , String message) {
        this.code = code ;
        this.message = message ;
    }
}
