package com.expensetracker.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ExpenseRequest {
    private String description;
    private double amount;
    private String category;
    private Date date;
}
