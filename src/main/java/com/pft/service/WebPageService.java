package com.pft.service;

import com.pft.entity.Expense;
import com.pft.entity.Income;
import com.pft.entity.User;

import java.util.List;

public interface WebPageService {

    String getName(String email);

    User getByEmail(String email);

    void saveUser(User user);

    void saveIncome(Income income);

    void saveExpense(Expense expense);

    List<Income> getIncomesByUser();

    void deleteIncome(Integer id);

    void updateIncome(Integer id, Income income);

    List<Expense> getExpensesByUser();

    void deleteExpense(Integer id);

    void updateExpense(Integer id, Expense expense);
}
