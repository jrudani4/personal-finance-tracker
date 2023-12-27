package com.pft.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pft.entity.enums.IncomeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "incomes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Income implements Serializable {

    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IncomeType incomeType;
    @Column(nullable = false)
    private Long incomeAmount;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Date incomeDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
