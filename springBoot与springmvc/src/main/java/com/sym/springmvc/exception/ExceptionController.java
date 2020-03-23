package com.sym.springmvc.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * springBoot的全局异常处理器
 *
 * Created by 沈燕明 on 2018/10/27.
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * {@link ExceptionHandler}注解指定要捕获的异常
     * 在方法内部实现对异常的处理逻辑
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView globalExceptionHandler(HttpServletRequest request,Exception e){
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg",e.getMessage());
        mv.setViewName("/error/500");
        return mv;
    }
}
