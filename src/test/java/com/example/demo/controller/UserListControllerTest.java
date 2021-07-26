package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserListController.class)
public class UserListControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;
    @MockBean
    ModelMapper modelMapper;

    @Test
    @WithMockUser(roles = {"GENERAL"}) // "ADMIN"でROLE_ADMIN
                                        // デフォルトはid: user, pass:password, ROLE_USER
    void getGeneralUserListTest() throws Exception {
        mockMvc.perform(
                // リクエスト情報を設定
                get("/user/list")
                        .accept(MediaType.ALL)
        )
                // 検証
                .andExpect(status().isOk()); // statusが200
    }

    @Test
    @WithAnonymousUser // 認証なしユーザ
    void getRedirectUserListTest() throws Exception {
        mockMvc.perform(
                // リクエスト情報を設定
                get("/user/list")
                        .accept(MediaType.ALL)
        )
                // 検証
                .andExpect(status().is3xxRedirection()); // statusが3xx
    }
}
