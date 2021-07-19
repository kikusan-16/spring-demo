package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.MUser;
import com.example.demo.model.Salary;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserServiceImplTest {

    UserServiceImpl userService;
    UserRepository repository;
    PasswordEncoder encoder;
    ExampleMatcher matcher;

    final String userId = "user@test.jp";

    @BeforeEach
    void setUp() {
        // モックを使用してサービスを初期化
        repository = mock(UserRepository.class);
        encoder = mock(PasswordEncoder.class);
        matcher = ExampleMatcher
                .matching() // and条件
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // Like句
                .withIgnoreCase(); // 大文字・小文字の両方
        userService = new UserServiceImpl(repository, encoder, matcher);
//        Mockito.when(repository.findById(userId)).thenReturn(
//                Optional.ofNullable(user));
//        Mockito.when(repository.save(user)).thenReturn(user);
//        Mockito.doNothing().when(repository).deleteById(userId);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void signupTest() {
        MUser user = new MUser();
        user.setUserId(userId);
        when(repository.existsById(user.getUserId())).thenReturn(false);
        when(repository.save(user)).thenReturn(user);

        userService.signup(user);
        // saveが１度呼ばれていることを検証
        verify(repository, times(1)).save(user);
    }

    @Test
    void getUsersTest() {
        MUser user = new MUser();
        when(repository.findAll(Example.of(user, matcher)))
                .thenReturn(List.of(
                    new MUser(),
                    new MUser(),
                    new MUser()
                )
        );

        List<MUser> userList = userService.getUsers(user);
        // 戻り値を検証
        assertEquals(3, userList.size());
        // 呼び出しを検証
        verify(repository, times(1)).findAll(Example.of(user, matcher));
    }

}
