package com.example.demo.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ControllerAdviceで全てのコントローラに共通するメソッドを用意できる
 * 共有できるのは、 @ExceptionHandler, @InitBinder, @ModelAttribute だけ
 */

@ControllerAdvice
public class GlobalControllAdvice {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model) {

        // 空文字をセット
        model.addAttribute("error", "");

        // メッセージをModelに登録
        model.addAttribute("message", "Exceptionが発生しました");

        // HTTPのエラーコード（500）をModelに登録
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }
}
