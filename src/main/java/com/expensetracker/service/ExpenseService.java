package com.expensetracker.service;

import com.expensetracker.dto.ExpenseRequest;
import com.expensetracker.dto.ExpenseResponse;
import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔒 Create an expense
    public ExpenseResponse createExpense(Long userId, ExpenseRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Date expenseDate = request.getDate() != null ? request.getDate() : new Date(); // Default to current date if not provided

        Expense expense = Expense.builder()
                .description(request.getDescription())
                .amount(request.getAmount())
                .category(request.getCategory())
                .date(expenseDate)
                .user(user)
                .build();

        expense = expenseRepository.save(expense);
        return new ExpenseResponse(expense.getId(), expense.getDescription(), expense.getAmount(), expense.getCategory(), expense.getDate());
    }

    // 🔒 Get all expenses for a user
    public List<ExpenseResponse> getAllExpenses(Long userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        return expenses.stream()
                .map(exp -> new ExpenseResponse(exp.getId(), exp.getDescription(), exp.getAmount(), exp.getCategory(), exp.getDate()))
                .collect(Collectors.toList());
    }

    // 🔒 Get expenses by category
    public List<ExpenseResponse> getExpensesByCategory(Long userId, String category) {
        List<Expense> expenses = expenseRepository.findByUserIdAndCategory(userId, category);
        return expenses.stream()
                .map(exp -> new ExpenseResponse(exp.getId(), exp.getDescription(), exp.getAmount(), exp.getCategory(), exp.getDate()))
                .collect(Collectors.toList());
    }

    // 🔒 Get expenses by date range
    public List<ExpenseResponse> getExpensesByDateRange(Long userId, Date startDate, Date endDate) {
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        return expenses.stream()
                .map(exp -> new ExpenseResponse(exp.getId(), exp.getDescription(), exp.getAmount(), exp.getCategory(), exp.getDate()))
                .collect(Collectors.toList());
    }


    public ExpenseResponse updateExpense(Long userId, Long expenseId, ExpenseRequest request) {
        Expense expense = expenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());

        Expense updatedExpense = expenseRepository.save(expense);

        return new ExpenseResponse(updatedExpense);
    }

    // Delete an expense
    public void deleteExpense(Long userId, Long expenseId) {
        Expense expense = expenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expenseRepository.delete(expense);
    }


}
