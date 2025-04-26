package com.expensetracker.repository;

import com.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId); // Get expenses by user ID
    List<Expense> findByUserIdAndCategory(Long userId, String category); // Find by category
    List<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate); // Find by date range

    Optional<Expense> findByIdAndUserId(Long expenseId, Long userId);
}
