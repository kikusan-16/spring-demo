package com.example.demo.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="t_salary")
public class Salary {

    @EmbeddedId // 主キーフィールドを表すクラスを指定する
    private SalaryKey salaryKey;

    private Integer salary;
}
