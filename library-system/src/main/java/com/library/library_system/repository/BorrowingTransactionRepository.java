package com.library.library_system.repository;

import com.library.library_system.model.Book;
import com.library.library_system.model.BorrowingTransaction;
import com.library.library_system.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    List<BorrowingTransaction> findByMemberAndReturnDateIsNull(Member member);

    boolean existsByBookAndReturnDateIsNull(Book book);
    BorrowingTransaction findByMemberAndBookAndReturnDateIsNull(Member member, Book book);
}
