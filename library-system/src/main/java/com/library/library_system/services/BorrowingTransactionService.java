package com.library.library_system.services;

import com.library.library_system.model.BorrowingTransaction;

import java.util.List;

public interface BorrowingTransactionService {

    String createBorrowing(Long memberId, Long bookId);

    String returnBook(Long memberId, Long bookId);

    List<BorrowingTransaction> getAllTransactions();

    BorrowingTransaction getTransactionById(Long id);
}
