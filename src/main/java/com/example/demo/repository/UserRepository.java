package com.example.demo.repository;

import com.example.demo.model.MUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * JPAリポジトリ<データモデルの型, 主キーの型>
 */
public interface UserRepository extends JpaRepository<MUser, String> {
//    /**
//     * ログインユーザー検索
//     * @Query はJPQLで記述する。nativeQuery属性にtrueを指定すればSQLでもかける
//     * :変数名 で埋め込みができる。 @Paramを使用すればパラメータ名と変数名を別にできる
//     */
//    @Query("select user from MUser user where userId = :userId")
//    public MUser findLoginUser(@Param("userId") String userId);
//
//    /**
//     * ユーザー更新
//     * Queryで更新をする場合は@Modifyingをつけなければならない
//     */
//    @Modifying
//    @Query(
//        "update MUser set"
//        + "password = :password,"
//        + "userName = :userName"
//        + "where userId = :userId"
//    )
//    public Integer updateUser(@Param("userId") String userId,
//                              @Param("password") String password,
//                              @Param("userName") String userName);
}
