package com.expensetracker.dto;

import com.expensetracker.entity.Expense;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExpenseResponse {

    private Long id;
    private String description;
    private double amount;
    private String category;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    // Constructor that maps the Expense entity to ExpenseResponse
    public ExpenseResponse(Expense expense) {
        this.id = expense.getId();
        this.description = expense.getDescription();
        this.amount = expense.getAmount();
        this.category = expense.getCategory();
        this.date = expense.getDate();
    }
}
