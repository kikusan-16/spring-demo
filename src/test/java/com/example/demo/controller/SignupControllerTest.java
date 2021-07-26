package com.example.demo.controller;

import com.example.demo.form.SignupForm;
import com.example.demo.model.MUser;
import com.example.demo.service.UserApplicationService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest // SpringBootApplicationがついたクラスを探して設定を自動で読み込む
//@AutoConfigureMockMvc // SpringBootTestでMockMvcを使用するのに必要
@WebMvcTest(SignupController.class) // ApplicationContextを使用せずにコントローラだけに焦点を当てる場合
                                    // MockBeanでTestするクラスにDIするクラスを指定する
public class SignupControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean // mockオブジェクトをDIする
    UserApplicationService userApplicationService;
    @MockBean
    UserService userService;
    @MockBean
    ModelMapper modelMapper;

    private final Locale locale = Locale.getDefault();

    @Test
    void getSignupTest() throws Exception {
        mockMvc.perform(
                // リクエスト情報を設定
                get("/user/signup")
                .accept(MediaType.ALL)
                .locale(locale)
        )
            // 検証
            .andExpect(status().isOk()) // statusが200
            .andExpect(view().name("user/signup")) // view名
            .andExpect(model().attributeExists("genderMap")); // model

        verify(userApplicationService, times(1)).getGenderMap(locale);
    }

    @Test
    void postSignupErrorsTest() throws Exception {
        SignupForm form = new SignupForm();

        mockMvc.perform(
                post("/user/signup")
                .flashAttr("signupForm", form)
        ).andExpect(model().hasErrors());

    }

    @Test
    void postSignupNoErrorsTest() throws Exception {
        SignupForm form = new SignupForm();
        form.setAge(20);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date date = df.parse("1997/01/01 00:00:00.000");
        form.setBirthday(date);
        form.setGender(1);
        form.setPassword("12345678");
        form.setUserId("user@exapmle.com");
        form.setUserName("test");

        mockMvc.perform(
                post("/user/signup")
                        .flashAttr("signupForm", form)
        ).andExpect(model().hasNoErrors());

    }

}
