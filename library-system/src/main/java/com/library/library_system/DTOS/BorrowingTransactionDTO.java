package com.library.library_system.DTOS;

import com.library.library_system.model.BorrowingTransaction;
import java.time.LocalDate;

public record BorrowingTransactionDTO(
        Long id,
        Long memberId,
        String memberName,
        Long bookCopyId,
        String bookTitle,
        LocalDate borrowDate,
        LocalDate dueDate,
        LocalDate returnDate
) {
    public static BorrowingTransactionDTO from(BorrowingTransaction t) {
        return new BorrowingTransactionDTO(
                t.getId(),
                t.getMember() != null ? t.getMember().getId() : null,
                t.getMember() != null ? t.getMember().getFullName() : null,
                t.getBookCopy() != null ? t.getBookCopy().getId() : null,
                t.getBookCopy() != null && t.getBookCopy().getBook() != null
                        ? t.getBookCopy().getBook().getTitle()
                        : null,
                t.getBorrowDate(),
                t.getDueDate(),
                t.getReturnDate()
        );
    }
}
