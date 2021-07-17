package com.example.demo.controller;

import com.example.demo.form.UserListForm;
import com.example.demo.model.MUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserListController {

    @Autowired
    private UserService userService;

    /** ユーザー一覧画面を表示 */
    @GetMapping("/list")
    public String getUserList(@ModelAttribute UserListForm form, Model model) {
        // ユーザー検索
        List<MUser> userList = userService.getUsers(new MUser());

        // Modelに登録
        model.addAttribute("userList", userList);

        return "user/list";
    }

    @PostMapping("/list")
    public String postUserList(@ModelAttribute UserListForm form, Model model) {
        // ユーザー検索
        List<MUser> userList = userService.getUsers(new MUser());

        // Modelに登録
        model.addAttribute("userList", userList);

        return "user/list";
    }

}
