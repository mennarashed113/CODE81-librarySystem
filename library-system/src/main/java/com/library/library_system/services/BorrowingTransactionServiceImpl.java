package com.library.library_system.services;



import com.library.library_system.DTOS.BorrowingTransactionDTO;
import com.library.library_system.model.*;
import com.library.library_system.repository.BookCopyRepository;
import com.library.library_system.repository.BorrowingTransactionRepository;
import com.library.library_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private BookCopyRepository bookCopyRepository;

    @Override
    public String createBorrowing(Long memberId, Long bookCopyId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new RuntimeException("Book copy not found"));

        // check max borrowed
        if (transactionRepository.findByMemberAndReturnDateIsNull(member).size() >= MAX_BORROWED_BOOKS) {
            return "Member has reached max limit of borrowed books.";
        }

        // check if copy already borrowed
        if (transactionRepository.existsByBookCopyAndReturnDateIsNull(copy)) {
            return "Book copy is currently borrowed by another member.";
        }

        BorrowingTransaction transaction = BorrowingTransaction.builder()
                .member(member)
                .bookCopy(copy)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .build();

        transactionRepository.save(transaction);

        //  mark the copy as borrowed
        copy.setStatus(BookCopyStatus.BORROWED);
        bookCopyRepository.save(copy);

        return "Borrowing recorded successfully.";
    }

    @Override
    public String returnBook(Long memberId, Long bookCopyId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new RuntimeException("Book copy not found"));

        BorrowingTransaction transaction =
                transactionRepository.findByMemberAndBookCopyAndReturnDateIsNull(member, copy);

        if (transaction == null) {
            return "No active borrowing record found for this member and book copy.";
        }

        transaction.setReturnDate(LocalDate.now());
        transactionRepository.save(transaction);

        // Mark copy as available again
        copy.setStatus(BookCopyStatus.AVAILABLE);
        bookCopyRepository.save(copy);

        return "Book returned successfully.";
    }

    @Override
    public List<BorrowingTransactionDTO> getAllTransactions() {
        List<BorrowingTransaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(BorrowingTransactionDTO::from)
                .toList();
    }

    @Override
    public BorrowingTransactionDTO getTransactionDTOById(Long id) {

            BorrowingTransaction t = transactionRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found with ID " + id));

            return BorrowingTransactionDTO.from(t);
        }
    }

