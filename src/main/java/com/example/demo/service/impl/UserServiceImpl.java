package com.example.demo.service.impl;

import com.example.demo.model.MUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Primary // インターフェイスを実装したクラスが複数あるとき、Primaryがついているものが優先でDIされる
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    // 検索条件
    ExampleMatcher matcher = ExampleMatcher
            .matching() // and条件
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // Like句
            .withIgnoreCase(); // 大文字・小文字の両方

    /** ユーザー登録 */
    @Transactional // 宣言的トランザクション メソッド内はトランザクション扱いとなる
    @Override
    public void signup(MUser user) {
        // 存在チェック
        if (repository.existsById(user.getUserId())) {
            throw new DataAccessException("ユーザーが既に存在"){};
        }

        user.setDepartmentId(1);
        user.setRole("ROLE_GENERAL");

        // パスワード暗号化
        String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));

        // insert
        repository.save(user);
    }

    /** ユーザー取得 */
    @Override
    public List<MUser> getUsers(MUser user) {
        return repository.findAll(Example.of(user, matcher));
    }

    /** ユーザー取得(1件) */
    @Override
    public MUser getUserOne(String userId) {
        Optional<MUser> option = repository.findById(userId);
        MUser user = option.orElse(null);
        return user;
    }

    /** ユーザー更新(1件) */
    @Transactional
    @Override
    public void updateUserOne(String userId, String password, String userName) {
        // パスワード暗号化
        String encryptPassword = encoder.encode(password);

        repository.findById(userId).map(user -> {
            user.setUserName(userName);
            user.setPassword(encryptPassword);
            return repository.save(user);
        }).orElseThrow(() -> {
            throw new DataAccessException("ユーザーが存在しない"){};
        });
    }

    /** ユーザー削除(1件) */
    @Transactional
    @Override
    public void deleteUserOne(String userId) {
        repository.deleteById(userId);
    }

    /** ログインユーザー情報取得 */
    @Override
    public MUser getLoginUser(String userId) {
        Optional<MUser> option = repository.findById(userId);
        MUser user = option.orElse(null);
        return user;
    }
}
