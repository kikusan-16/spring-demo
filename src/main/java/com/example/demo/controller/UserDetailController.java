package com.example.demo.controller;

import com.example.demo.form.UserDetailForm;
import com.example.demo.model.MUser;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserDetailController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * ユーザー詳細画面を表示
     * メールアドレスを正しく受け取るために正規表現を使用
     */
    @GetMapping("/detail/{userId:.+}")
    public String getUser(UserDetailForm form, Model model,
                          @PathVariable("userId") String userId) {
        MUser user = userService.getUserOne(userId);
        user.setPassword(null); // デフォルトは空に

        form = modelMapper.map(user, UserDetailForm.class);
        form.setSalaryList(user.getSalaryList());

        // Modelに登録
        model.addAttribute("userDetailForm", form);

        return "user/detail";
    }

    /**
     * ユーザー更新処理
     * paramsはbuttonタグのname属性と同じ値
     */
    @PostMapping(value = "/detail", params = "update")
    public String updateUser(UserDetailForm form, Model model) {
        // ユーザーを更新
        userService.updateUserOne(form.getUserId(), form.getPassword(), form.getUserName());

        return "redirect:/user/list";
    }

    /** ユーザー削除処理 */
    @PostMapping(value = "/detail", params = "delete")
    public String deleteUser(UserDetailForm form, Model model) {
        // ユーザーを削除
        userService.deleteUserOne(form.getUserId());

        return "redirect:/user/list";
    }
}
