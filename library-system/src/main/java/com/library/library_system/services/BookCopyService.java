package com.library.library_system.services;

import com.library.library_system.DTOS.BookCopyDTO;
import com.library.library_system.model.BookCopyStatus;

import java.util.List;

public interface BookCopyService {
    BookCopyDTO createCopy(Long bookId, String barcode);
    List<BookCopyDTO> getCopiesByBook(Long bookId);


    BookCopyDTO updateBookCopy(Long copyId, BookCopyDTO copyDTO);

    void deleteBookCopy(Long copyId);
}
