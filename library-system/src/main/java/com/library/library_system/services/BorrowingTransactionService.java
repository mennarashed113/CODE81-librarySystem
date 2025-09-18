package com.library.library_system.services;

import com.library.library_system.DTOS.BorrowingTransactionDTO;
import com.library.library_system.model.BorrowingTransaction;

import java.util.List;

public interface BorrowingTransactionService {


    String createBorrowing(Long memberId, Long bookCopyId);


    String returnBook(Long memberId, Long bookCopyId);


    List<BorrowingTransactionDTO> getAllTransactions();




    BorrowingTransactionDTO getTransactionDTOById(Long id);
}
