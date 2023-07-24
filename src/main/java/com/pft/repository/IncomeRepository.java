package com.pft.repository;

import com.pft.entity.Income;
import com.pft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

    List<Income> findByUser(User user);

    @Query(value = "SELECT sum(i.income_amount) FROM incomes i WHERE i.user_id = :userId", nativeQuery = true)
    Long findTotalIncomeByUser(Integer userId);
}
