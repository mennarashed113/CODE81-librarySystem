package com.library.library_system.services;



import com.library.library_system.model.Book;
import com.library.library_system.model.BorrowingTransaction;
import com.library.library_system.model.Member;
import com.library.library_system.repository.BookRepository;
import com.library.library_system.repository.BorrowingTransactionRepository;
import com.library.library_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingTransactionServiceImpl implements BorrowingTransactionService {

    private static final int MAX_BORROWED_BOOKS = 3;

    @Autowired
    private BorrowingTransactionRepository transactionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public String createBorrowing(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        if (transactionRepository.findByMemberAndReturnDateIsNull(member).size() >= MAX_BORROWED_BOOKS) {
            return "Member has reached max limit of borrowed books.";
        }

        if (transactionRepository.existsByBookAndReturnDateIsNull(book)) {
            return "Book is currently borrowed by another member.";
        }

        BorrowingTransaction transaction = BorrowingTransaction.builder()
                .member(member)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .build();
        transactionRepository.save(transaction);
        return "Borrowing recorded successfully.";
    }

    @Override
    public String returnBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        BorrowingTransaction transaction = transactionRepository.findByMemberAndBookAndReturnDateIsNull(member, book);
        if (transaction == null) {
            return "No active borrowing record found for this member and book.";
        }

        transaction.setReturnDate(LocalDate.now());
        transactionRepository.save(transaction);
        return "Book returned successfully.";
    }

    @Override
    public List<BorrowingTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public BorrowingTransaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
}
