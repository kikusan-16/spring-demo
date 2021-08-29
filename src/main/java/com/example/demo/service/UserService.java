package com.example.demo.service;

import com.example.demo.model.MUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * UserService用インターフェイス
 * このインターフェイスを@AutoWired すると、自動で実装のほうのクラスが入る
 */
public interface UserService {

    /** ユーザー登録 */
    public void signup (MUser user);

    /** ユーザー取得 */
    public Page<MUser> getUsers(MUser user, Pageable pageable);

    /** ユーザー取得(1件) */
    public MUser getUserOne(String userId);

    /** ユーザー更新(1件) */
    public void updateUserOne(String userId, String password, String userName);

    /** ユーザー削除(1件) */
    public void deleteUserOne(String userId);

    /** ログインユーザー情報取得 */
    public MUser getLoginUser(String userId);

}
