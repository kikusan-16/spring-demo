package com.example.demo.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable // 主キーのカラムが2つ以上の場合作らなければいけない主キークラスにつける
public class SalaryKey implements Serializable {
    private String userId;
    private String yearMonth;
}
