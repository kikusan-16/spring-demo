package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="m_department")
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    private Integer departmentId;
    private String departmentName;
}

