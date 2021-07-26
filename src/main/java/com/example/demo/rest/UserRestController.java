package com.example.demo.rest;

import com.example.demo.form.SignupForm;
import com.example.demo.form.UserDetailForm;
import com.example.demo.form.ValidGroupOrder;
import com.example.demo.model.MUser;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageSource messageSource;

    /** ユーザーを登録 */
    @PostMapping("/signup/rest")
    public RestResult postSignup(@Validated(ValidGroupOrder.class) SignupForm form,
                                 BindingResult bindingResult,
                                 Locale locale) {
        // 入力チェック結果
        if (bindingResult.hasErrors()) {
            // NG
            Map<String, String> errors = new HashMap<>();

            // エラーメッセージ取得
            for (FieldError error : bindingResult.getFieldErrors()) {
                String message = messageSource.getMessage(error, locale);
                errors.put(error.getField(), message);
            }
            // エラー結果の返却
            return new RestResult(-1, errors);
        }

        // formをMUserクラスに変換
        MUser user = modelMapper.map(form, MUser.class);

        // ユーザー登録
        userService.signup(user);

        // 結果の返却
        return new RestResult(0, null);
    }


    /** ユーザーを更新 */
    @PutMapping("/update")
    public int updateUser(UserDetailForm form) {
        // ユーザーを更新
        userService.updateUserOne(form.getUserId(), form.getPassword(), form.getUserName());
        return 0;
    }

    /** ユーザーを更新 */
    @DeleteMapping("/delete")
    public int deleteUser(UserDetailForm form) {
        // ユーザーを削除
        userService.deleteUserOne(form.getUserId());

        return 0;
    }

}