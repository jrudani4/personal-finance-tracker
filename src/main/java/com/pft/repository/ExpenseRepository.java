package com.pft.repository;

import com.pft.entity.Expense;
import com.pft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    List<Expense> findByUser(User user);

    @Query(value = "SELECT sum(e.expense_amount) FROM expenses e WHERE e.user_id = :userId", nativeQuery = true)
    Long findTotalExpenseByUser(Integer userId);
}
