package com.sym.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author shenyanming
 * @date 2020/8/3 21:51.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * {@link ExceptionHandler}注解指定要捕获的异常
     * 在方法内部实现对异常的处理逻辑
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handler(HttpServletRequest request, Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", e.getMessage());
        mv.setViewName("/error/500");
        return mv;
    }

}
