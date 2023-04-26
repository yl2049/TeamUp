//package com.team033.teamup.util;
//
//import cn.hutool.core.util.StrUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@Slf4j
//@RestControllerAdvice
//public class ExceptionHandle {
//
//    @ExceptionHandler(value = ServiceException.class)
//    public Result serviceExceptionError(ServiceException e) {
//        log.error("serviceExceptionError: ", e);
//        String code = e.getCode();
//        if (StrUtil.isNotBlank(code)) {
//            return Result.error(false, e.getMessage());
//        }
//        return Result.error(false, e.getMessage());
//    }
//
//    @ExceptionHandler(value = Exception.class)
//    public Result exceptionError(Exception e) {
//        log.error("Exception", e);
//        return Result.error(false,e.getMessage());
//    }
//
//}
