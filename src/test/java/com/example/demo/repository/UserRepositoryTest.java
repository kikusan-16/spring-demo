package com.example.demo.repository;

import com.example.demo.model.MUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest // テストごとにロールバック、インメモリDBを使用する
// schema.sql, data.sqlによって初期データ生成
// 追加のSQLは@Sqlで指定
//@Sql(scripts = "classpath:/user-test.sql",
//        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;
    @Autowired
    JdbcTemplate jdbcTemplate; // テーブル情報閲覧用
    @Autowired
    EntityManager entityManager; // コミット用

    @Test
    @Transactional // メソッドごとにロールバック
    void ユーザー登録() {
        MUser user = new MUser();
        user.setUserId("user@example.com");
        repository.save(user);
        entityManager.flush();
        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "m_user");
        assertEquals(13, count);
    }

    @Test
    @Transactional
    void 全件検索() {
        List<MUser> users = repository.findAll();
        assertEquals(12, users.size());
    }
}
