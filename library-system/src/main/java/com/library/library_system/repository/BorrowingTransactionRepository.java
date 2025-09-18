package com.library.library_system.repository;

import com.library.library_system.model.Book;
import com.library.library_system.model.BookCopy;
import com.library.library_system.model.BorrowingTransaction;
import com.library.library_system.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    List<BorrowingTransaction> findByMemberAndReturnDateIsNull(Member member);

    // Check if a copy is currently borrowed
    boolean existsByBookCopyAndReturnDateIsNull(BookCopy bookCopy);

    // Get the active transaction for a member + a specific copy
    BorrowingTransaction findByMemberAndBookCopyAndReturnDateIsNull(Member member, BookCopy bookCopy);
}
