package com.example.demo.form;

import javax.validation.GroupSequence;

// 左のGroupから先にバリデーションされる
@GroupSequence({ ValidGroup1.class, ValidGroup2.class })
public interface ValidGroupOrder {
}
