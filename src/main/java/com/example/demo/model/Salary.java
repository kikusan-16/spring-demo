package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="t_salary")
@NoArgsConstructor
@AllArgsConstructor
public class Salary {

    @EmbeddedId // 主キーフィールドを表すクラスを指定する
    private SalaryKey salaryKey;

    private Integer salary;
}
