package com.example.demo.service;

import com.example.demo.model.MUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserServiceImplTest {

    // モックするべきオブジェクトがDIできるように書いていないとテストできない。
    UserServiceImpl userService;
    UserRepository repository;
    PasswordEncoder encoder;
    ExampleMatcher matcher;

    final String userId = "user999@test.jp";

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
    }

    @AfterEach
    void tearDown() {}

    @Test
    void ユーザー登録() {
        MUser user = new MUser();
        user.setUserId(userId);
        when(repository.existsById(user.getUserId())).thenReturn(false);
        when(repository.save(user)).thenReturn(user);

        userService.signup(user);
        // saveが１度呼ばれていることを検証
        verify(repository, times(1)).save(user);
    }

    @Test
    void ユーザー一覧() {
        Page<MUser> userPage = mock(Page.class);

        MUser user = new MUser();
        Pageable pageable = PageRequest.of(0,5);
        when(repository.findAll(Example.of(user, matcher), pageable))
                .thenReturn(userPage);

        Page<MUser> page = userService.getUsers(user, pageable);
        // 戻り値を検証
        assertEquals(page, userPage);
        // 呼び出しを検証
        verify(repository, times(1)).findAll(Example.of(user, matcher), pageable);
    }
}
