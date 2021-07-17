package com.example.demo.repository;

import com.example.demo.model.MUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPAリポジトリ<データモデルの型, 主キーの型>
 */
public interface UserRepository extends JpaRepository<MUser, String> {
}
