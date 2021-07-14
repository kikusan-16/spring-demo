package com.example.demo.controller;

import com.example.demo.form.SignupForm;
import com.example.demo.form.ValidGroupOrder;
import com.example.demo.service.UserApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j // lombokで log static変数追加
public class SignupController {
    private final UserApplicationService userApplicationService;

    @GetMapping("/signup")
    public String getSignup(Model model, Locale locale,
                            @ModelAttribute SignupForm form) {
        // 引数のModelでthymeleaf画面にAttributeをセットできる
        // @ModelAttributeで model.addAttribute("signupForm", form); を自動化
        Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
        model.addAttribute("genderMap", genderMap);

        return "user/signup";
    }

    @PostMapping("/signup")
    public String postSignup(Model model, Locale locale,
                             @ModelAttribute @Validated(ValidGroupOrder.class) SignupForm form,
                             BindingResult bindingResult) {
        // @ModelAttribute でformから送られたデータをFormクラスにセットする
        // BindingResult でバリデーションエラーを探せる。
        // @Validated でバリデーションを実行する。GroupSequenceのinterfaceでバリデーション順序を指定できる
        if (bindingResult.hasErrors()) {
            return getSignup(model, locale, form);
        }
        log.info(form.toString());
        // redirect PRGパターン リロードでのPost送信を避ける。
        return "redirect:/login";
    }
}
