package com.pft.repository;

import com.pft.entity.Income;
import com.pft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

    List<Income> findByUser(User user);
}
