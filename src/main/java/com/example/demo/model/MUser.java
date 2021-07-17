package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@Entity // jpaマッピング
@Table(name = "m_user")
public class MUser {
    @Id
    private String userId;
    private String password;
    private Date birthday;
    private Integer age;
    private Integer gender;
    private Integer departmentId;
    private String role;
    @Transient // O/R マッピングしないフィールド
    private Department department;
    @Transient
    private List<Salary> salaryList;
}
