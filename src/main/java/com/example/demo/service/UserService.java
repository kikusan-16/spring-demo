package com.example.demo.service;

import com.example.demo.model.MUser;

import java.util.List;

/**
 * UserService用インターフェイス
 * このインターフェイスを@AutoWired すると、自動で実装のほうのクラスが入る
 */
public interface UserService {

    /** ユーザー登録 */
    public void signup (MUser user);

    /** ユーザー取得 */
    public List<MUser> getUsers(MUser user);

    /** ユーザー取得(1件) */
    public MUser getUserOne(String userId);

    /** ユーザー更新(1件) */
    public void updateUserOne(String userId, String password, String userName);

    /** ユーザー削除(1件) */
    public void deleteUserOne(String userId);

}