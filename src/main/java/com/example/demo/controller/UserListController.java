package com.example.demo.controller;

import com.example.demo.form.UserListForm;
import com.example.demo.model.MUser;
import com.example.demo.service.UserService;
import com.example.demo.view.SampleExcel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserListController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /** ユーザー一覧画面を表示 */
    @GetMapping("/list")
    public String getUserList(@ModelAttribute UserListForm form, Model model,
                              @PageableDefault(page = 0, size = 5, sort = "userId") Pageable pageable) {
        // formは必ずパラメータが空

        // formをMUserクラスに変換
        MUser user = modelMapper.map(form, MUser.class);

        // ユーザー検索
        Page<MUser> userPage = userService.getUsers(user, pageable);

        // Modelに登録
        model.addAttribute("userPage", userPage);
        model.addAttribute("userList", userPage.getContent());

        return "user/list";
    }

    /** ユーザー検索処理
     * TODO: 検索時のページングが一覧に戻るようになっている
     * */
    @PostMapping("/list")
    public String postUserList(@ModelAttribute UserListForm form, Model model,
                               @PageableDefault(page = 0, size = 5, sort = "userId") Pageable pageable) {

        // formをMUserクラスに変換
        MUser user = modelMapper.map(form, MUser.class);

        // ユーザー検索
        Page<MUser> userPage = userService.getUsers(user, pageable);

        // Modelに登録
        model.addAttribute("userPage", userPage);
        model.addAttribute("userList", userPage.getContent());

        return "user/list";
    }

    /** Excel出力 */
    @GetMapping("/excel")
    public SampleExcel writeUserExcel(SampleExcel excel){
        // AbstractXlsxView を継承したクラスを引数に受け取り、値をセットして返却すると、excelがビルドされる
        excel.addStaticAttribute("currentTime", new java.util.Date());
        excel.addStaticAttribute("fileName","sampleExcel.xlsx");
        return excel;
    }


}
