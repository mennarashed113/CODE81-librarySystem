package com.library.library_system.services;

import com.library.library_system.DTOS.BookDTO;
import com.library.library_system.model.Book;

import java.util.List;

public interface BookService {
    Book saveBook(Book book);
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO updateBook(Long id, BookDTO updatedBook);
    void deleteBook(Long id);
}