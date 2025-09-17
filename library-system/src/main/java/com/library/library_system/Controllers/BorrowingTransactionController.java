package com.library.library_system.Controllers;

import com.library.library_system.model.BorrowingTransaction;
import com.library.library_system.services.BorrowingTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingTransactionController {

    @Autowired
    private BorrowingTransactionService transactionService;

    @PostMapping
    public String create(@RequestParam Long memberId, @RequestParam Long bookId) {
        return transactionService.createBorrowing(memberId, bookId);
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        return transactionService.returnBook(memberId, bookId);
    }

    @GetMapping
    public List<BorrowingTransaction> getAll() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public BorrowingTransaction getById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }
}
