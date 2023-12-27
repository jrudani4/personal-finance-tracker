package com.pft.service.impl;

import com.pft.entity.Expense;
import com.pft.entity.Income;
import com.pft.entity.User;
import com.pft.repository.ExpenseRepository;
import com.pft.repository.IncomeRepository;
import com.pft.repository.UserRepository;
import com.pft.service.WebPageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebPageServiceImpl implements WebPageService {

    private final UserRepository userRepository;

    private final IncomeRepository incomeRepository;

    private final ExpenseRepository expenseRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public String getName(String email) {
        return userRepository.findName(email);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with: " + email));
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void saveIncome(Income income) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        income.setUser(getByEmail(email));
        incomeRepository.save(income);
    }

    @Override
    public void saveExpense(Expense expense) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        expense.setUser(getByEmail(email));
        expenseRepository.save(expense);
    }

    @Override
    public List<Income> getIncomesByUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return incomeRepository.findByUser(getByEmail(email));
    }

    @Override
    public void deleteIncome(Integer id) {
        incomeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateIncome(Integer id, Income income) {
        Income existingIncome = incomeRepository.findById(id).orElseThrow();
        existingIncome.setIncomeType(income.getIncomeType());
        existingIncome.setIncomeAmount(income.getIncomeAmount());
        existingIncome.setDescription(income.getDescription());
        existingIncome.setIncomeDate(income.getIncomeDate());
        incomeRepository.save(existingIncome);
    }

    @Override
    public List<Expense> getExpensesByUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return expenseRepository.findByUser(getByEmail(email));
    }

    @Override
    public void deleteExpense(Integer id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public void updateExpense(Integer id, Expense expense) {
        Expense existingExpense = expenseRepository.findById(id).orElseThrow();
        existingExpense.setExpenseType(expense.getExpenseType());
        existingExpense.setExpenseAmount(expense.getExpenseAmount());
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setExpenseDate(expense.getExpenseDate());
        expenseRepository.save(existingExpense);
    }

    @Override
    public Long getTotalIncomeByUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (incomeRepository.findTotalIncomeByUser(getByEmail(email).getId()) == null) {
            return 0L;
        }
        return incomeRepository.findTotalIncomeByUser(getByEmail(email).getId());
    }

    @Override
    public Long getTotalExpenseByUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (expenseRepository.findTotalExpenseByUser(getByEmail(email).getId()) == null) {
            return 0L;
        }
        return expenseRepository.findTotalExpenseByUser(getByEmail(email).getId());
    }
}
