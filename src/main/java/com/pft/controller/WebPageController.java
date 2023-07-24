package com.pft.controller;

import com.pft.entity.Expense;
import com.pft.entity.Income;
import com.pft.entity.User;
import com.pft.service.WebPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class WebPageController {

    @Autowired
    private WebPageService service;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/")
    public String homePage(Model model, Principal principal) {
        model.addAttribute("username", service.getName(principal.getName()));
        model.addAttribute("totalIncome", service.getTotalIncomeByUser());
        model.addAttribute("totalExpense", service.getTotalExpenseByUser());
        System.out.println(service.getTotalIncomeByUser());
        System.out.println(service.getTotalExpenseByUser());
        return "home";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        try {
            if (service.getByEmail(user.getEmail()) != null)
                return "redirect:/register?error";

        } catch (UsernameNotFoundException ignored) {
        }

        service.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/addIncome")
    public String addIncomeModal(Model model) {
        model.addAttribute("income", new Income());
        return "redirect:/viewIncome";
    }

    @PostMapping("/addIncome")
    public String addIncomeService(@ModelAttribute("income") Income income) {
        service.saveIncome(income);
        return "redirect:/viewIncome?incomeAdded";
    }

    @GetMapping("/addExpense")
    public String addExpenseModal(Model model) {
        model.addAttribute("expense", new Expense());
        return "redirect:/viewExpense";
    }

    @PostMapping("/addExpense")
    public String addExpenseService(@ModelAttribute("expense") Expense expense) {
        service.saveExpense(expense);
        return "redirect:/viewExpense?expenseAdded";
    }

    @GetMapping("/viewIncome")
    public String viewIncomePage(Model model, Principal principal) {
        model.addAttribute("incomesByUser", service.getIncomesByUser());
        model.addAttribute("username", service.getName(principal.getName()));
        model.addAttribute("incomeUpdate", new Income());
        return "viewIncome";
    }

    @GetMapping("/deleteIncome/{id}")
    public String deleteIncome(@PathVariable Integer id) {
        service.deleteIncome(id);
        return "redirect:/viewIncome?incomeDeleted";
    }

    @PostMapping("/updateIncome/{id}")
    public String updateIncome(@PathVariable Integer id, @ModelAttribute("incomeUpdate") Income income) {
        service.updateIncome(id, income);
        return "redirect:/viewIncome?incomeUpdated";
    }

    @GetMapping("/viewExpense")
    public String viewExpensePage(Model model, Principal principal) {
        model.addAttribute("expensesByUser", service.getExpensesByUser());
        model.addAttribute("username", service.getName(principal.getName()));
        model.addAttribute("expenseUpdate", new Income());
        return "viewExpense";
    }

    @GetMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable Integer id) {
        service.deleteExpense(id);
        return "redirect:/viewExpense?expenseDeleted";
    }

    @PostMapping("/updateExpense/{id}")
    public String updateExpense(@PathVariable Integer id, @ModelAttribute("expenseUpdate") Expense expense) {
        service.updateExpense(id, expense);
        return "redirect:/viewExpense?expenseUpdated";
    }
}
