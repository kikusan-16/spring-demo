package com.example.demo.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class RestResult {

    private int result;

    /** エラーマップ
     * key: フィールド名
     * value: エラーメッセージ
     */
    private Map<String, String> errors;
}
