package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity // jpaマッピング
@Table(name = "m_user")
@NoArgsConstructor
@AllArgsConstructor
public class MUser {
    @Id
    private String userId;
    private String password;
    private String userName;
    private Date birthday;
    private Integer age;
    private Integer gender;
    private Integer departmentId;
    private String role;

    // @Transient // O/R マッピングしないフィールド

    // @ManyToOne 後者が自クラス Optional=trueはnullを許可する = left join. falseならinner join
    @ManyToOne(optional = true)
    // 結合先のkeyカラムを指定 このオブジェクトから結合先もinsert, updateするかを指定
    @JoinColumn(insertable = false, updatable = false, name = "departmentId")
    private Department department;

    @OneToMany
    @JoinColumn(insertable = false, updatable = false, name = "userId")
    private List<Salary> salaryList;
}
