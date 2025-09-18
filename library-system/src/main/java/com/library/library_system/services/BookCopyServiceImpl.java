package com.library.library_system.services;

import com.library.library_system.DTOS.BookCopyDTO;
import com.library.library_system.model.Book;
import com.library.library_system.model.BookCopy;
import com.library.library_system.model.BookCopyStatus;
import com.library.library_system.repository.BookCopyRepository;
import com.library.library_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookCopyServiceImpl implements BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookCopyDTO createCopy(Long bookId, String barcode) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Create new BookCopy
        BookCopy copy = new BookCopy();
        copy.setBook(book);
        copy.setBarcode(barcode);
        copy.setStatus(BookCopyStatus.AVAILABLE);

        // Save it to DB
        BookCopy savedCopy = bookCopyRepository.save(copy);

        // Map to DTO and return
        return BookCopyDTO.from(savedCopy);
    }

    @Override
    public List<BookCopyDTO> getCopiesByBook(Long bookId) {
        List<BookCopy> copies = bookCopyRepository.findByBookId(bookId);

        return copies.stream()
                .map(BookCopyDTO::from)
                .toList();
    }

    @Transactional
    @Override
    public BookCopyDTO updateBookCopy(Long copyId, BookCopyDTO copyDTO) {
        BookCopy copy = bookCopyRepository.findById(copyId)
                .orElseThrow(() -> new RuntimeException("BookCopy not found"));


        if (copyDTO.barcode() != null) {
            copy.setBarcode(copyDTO.barcode());
        }
        if (copyDTO.location() != null) {
            copy.setLocation(copyDTO.location());
        }


        BookCopy updatedCopy = bookCopyRepository.save(copy);
        return BookCopyDTO.from(updatedCopy);
    }

    @Transactional
    public void deleteBookCopy(Long copyId) {
        if (!bookCopyRepository.existsById(copyId)) {
            throw new RuntimeException("BookCopy not found");
        }
        bookCopyRepository.deleteById(copyId);
    }
}
