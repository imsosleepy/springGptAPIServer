package com.chatgpt.demo.exception;

import com.chatgpt.demo.model.ResponseVo;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(BindException.class)
    public @ResponseBody ResponseVo methodValidException(BindException e, HttpServletRequest request){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(400);
        responseVo.setResponse("올바른 파라미터를 입력해주세요");
        return responseVo;
    }

}
