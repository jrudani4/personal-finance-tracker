package com.pft.service.impl;

import com.pft.entity.Expense;
import com.pft.entity.Income;
import com.pft.entity.User;
import com.pft.repository.ExpenseRepository;
import com.pft.repository.IncomeRepository;
import com.pft.repository.UserRepository;
import com.pft.service.WebPageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebPageServiceImpl implements WebPageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
}
