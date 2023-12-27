package com.pft.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pft.entity.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "expenses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense implements Serializable {

    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;
    @Column(nullable = false)
    private Long expenseAmount;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Date expenseDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
