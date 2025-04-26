//package com.expensetracker.controller;
//
//import com.expensetracker.dto.ExpenseRequest;
//import com.expensetracker.entity.*;
//import com.expensetracker.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/expenses")
//@RequiredArgsConstructor
//public class ExpenseController {
//
//    private final ExpenseRepository expenseRepo;
//    private final UserRepository userRepo;
//
//    @GetMapping
//    public List<Expense> getAll(Authentication auth) {
//        User user = userRepo.findByEmail(auth.getName()).orElseThrow();
//        return expenseRepo.findByUserId(user.getId());
//    }
//
//    @PostMapping
//    public Expense add(@RequestBody ExpenseRequest dto, Authentication auth) {
//        User user = userRepo.findByEmail(auth.getName()).orElseThrow();
//        Expense exp = Expense.builder()
//                .title(dto.getTitle())
//                .amount(dto.getAmount())
//                .category(dto.getCategory())
//                .date(dto.getDate())
//                .user(user)
//                .build();
//        return expenseRepo.save(exp);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        expenseRepo.deleteById(id);
//    }
//}


package com.expensetracker.controller;

import com.expensetracker.dto.ExpenseRequest;
import com.expensetracker.dto.ExpenseResponse;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private JwtUtil jwtUtil;

    // 🔒 Create an expense
    @PostMapping("/create")
    public ResponseEntity<ExpenseResponse> createExpense(@RequestHeader("Authorization") String token, @RequestBody ExpenseRequest request) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        ExpenseResponse response = expenseService.createExpense(userId, request);
        return ResponseEntity.ok(response);
    }

    // 🔒 Get all expenses
    @GetMapping("/all")
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        List<ExpenseResponse> response = expenseService.getAllExpenses(userId);
        return ResponseEntity.ok(response);
    }

    // 🔒 Get expenses by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByCategory(@RequestHeader("Authorization") String token, @PathVariable String category) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        List<ExpenseResponse> response = expenseService.getExpensesByCategory(userId, category);
        return ResponseEntity.ok(response);
    }

    // 🔒 Get expenses by date range
    @GetMapping("/date")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByDateRange(@RequestHeader("Authorization") String token,
                                                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        List<ExpenseResponse> response = expenseService.getExpensesByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody ExpenseRequest request) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        ExpenseResponse response = expenseService.updateExpense(userId, id, request);
        return ResponseEntity.ok(response);
    }

    // 🔒 Delete an expense
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExpense(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        expenseService.deleteExpense(userId, id);
        return ResponseEntity.ok("Expense deleted successfully.");
    }
}
