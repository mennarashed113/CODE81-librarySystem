package com.library.library_system.Controllers;

import com.library.library_system.DTOS.BorrowingTransactionDTO;
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

    // Borrow a specific book copy
    @PostMapping
    public String create(@RequestParam Long memberId, @RequestParam Long bookCopyId) {
        return transactionService.createBorrowing(memberId, bookCopyId);
    }

    // Return a specific book copy
    @PostMapping("/return")
    public String returnBook(@RequestParam Long memberId, @RequestParam Long bookCopyId) {
        return transactionService.returnBook(memberId, bookCopyId);
    }


    @GetMapping
    public List<BorrowingTransactionDTO> getAll() {
        return transactionService.getAllTransactions();
    }
    @GetMapping("/{id}")
    public BorrowingTransaction getById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }
}
