package com.library.library_system.DTOS;

import com.library.library_system.model.BookCopy;

public record BookCopyDTO(
        Long id,
        String barcode,
        String status,
        String location,
        Long bookId,
        String bookName
) {
    public static BookCopyDTO from(BookCopy copy) {
        return new BookCopyDTO(
                copy.getId(),
                copy.getBarcode(),
                copy.getStatus() != null ? copy.getStatus().name() : null,
                copy.getLocation(),
                copy.getBook() != null ? copy.getBook().getId() : null,
                copy.getBook() != null ? copy.getBook().getTitle() : null
        );
    }
}