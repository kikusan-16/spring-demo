package com.example.demo.controller;

import com.example.demo.form.SignupForm;
import com.example.demo.form.ValidGroupOrder;
import com.example.demo.model.MUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserApplicationService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.Locale;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j // lombokで log static変数追加
public class SignupController {
    private final UserApplicationService userApplicationService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping("/signup")
    public String getSignup(Model model, Locale locale,
                            @ModelAttribute SignupForm form) {
        // 引数のModelでthymeleaf画面にAttributeをセットできる
        // @ModelAttributeで model.addAttribute("signupForm", form); を自動化かつ、
        //                   リクエストボディのバインド。Modelに該当オブジェクトがないとnewする。
        Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
        model.addAttribute("genderMap", genderMap);

        return "user/signup";
    }

    @PostMapping("/signup")
    public String postSignup(Model model, Locale locale,
                             @ModelAttribute @Validated(ValidGroupOrder.class) SignupForm form,
                             BindingResult bindingResult) {
        // @ModelAttribute でformから送られたデータをFormクラスにセットする(バインド)
        // BindingResult でバリデーションエラーを探せる。
        // @Validated でバリデーションを実行する。GroupSequenceのinterfaceでバリデーション順序を指定できる
        if (bindingResult.hasErrors()) {
            return getSignup(model, locale, form);
        }
        log.info(form.toString());

        // formをMUserクラスに変換
        // modelを直接受け取るより、拡張性が上がる
        MUser user = modelMapper.map(form, MUser.class);

        // ユーザー登録
        userService.signup(user);

        // redirect PRGパターン リロードでのPost送信を避ける。
        return "redirect:/login";
    }

    /**
     * クラス毎例外処理
     * @ExceptionHandler でこのクラスに指定エクセプションがでたときの処理を記述できる
     * userIdの重複をチェックしていないので、被ると投げられる
     */
    @ExceptionHandler(DataAccessException.class)
    public String exceptionHandler(Exception e, Model model) {
        // 空文字をセット
        model.addAttribute("error","");
        // メッセージをModelに登録
        model.addAttribute("message","SignupControllerでDataAccessExceptionが発生しました");
        // HTTPのエラーコード（500）をModelに登録
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }

}
