package com.library.library_system.services;

import com.library.library_system.DTOS.BookDTO;
import com.library.library_system.model.Author;
import com.library.library_system.model.Book;
import com.library.library_system.model.Category;
import com.library.library_system.model.Publisher;
import com.library.library_system.repository.AuthorRepository;
import com.library.library_system.repository.BookRepository;
import com.library.library_system.repository.CategoryRepository;
import com.library.library_system.repository.PublisherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Book saveBook(Book bookRequest) {


        Publisher publisher = publisherRepository.findByName(bookRequest.getPublisher().getName())
                .orElseGet(() -> publisherRepository.save(bookRequest.getPublisher()));
        bookRequest.setPublisher(publisher);


        Set<Author> resolvedAuthors = new HashSet<>();
        for (Author a : bookRequest.getAuthors()) {
            Author existing = authorRepository.findByName(a.getName())
                    .orElseGet(() -> authorRepository.save(a));
            resolvedAuthors.add(existing);
        }
        bookRequest.setAuthors(resolvedAuthors);


        Set<Category> resolvedCategories = new HashSet<>();
        for (Category c : bookRequest.getCategories()) {
            Category existing = categoryRepository.findByName(c.getName())
                    .orElseGet(() -> categoryRepository.save(c));
            resolvedCategories.add(existing);
        }
        bookRequest.setCategories(resolvedCategories);


        return bookRepository.save(bookRequest);
    }


    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        System.out.println("Books from DB: " + books.size());
        for (Book b : books) {
            System.out.println("Book: " + b.getTitle() + " authors: " + b.getAuthors());
        }
        return books.stream().map(BookDTO::from).collect(Collectors.toList());
    }


    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));


        return BookDTO.from(book);
    }

    @Override
    @Transactional
    public BookDTO updateBook(Long id, BookDTO updatedBookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Update basic fields
        existingBook.setTitle(updatedBookDTO.title());
        existingBook.setLanguage(updatedBookDTO.language());
        existingBook.setIsbn(updatedBookDTO.isbn());
        existingBook.setEdition(updatedBookDTO.edition());
        existingBook.setPublicationYear(updatedBookDTO.publicationYear());
        existingBook.setSummary(updatedBookDTO.summary());
        existingBook.setCoverImageUrl(updatedBookDTO.coverImageUrl());


        Book saved = bookRepository.save(existingBook);

        // return as DTO to avoid lazy errors
        return BookDTO.from(saved);
    }


    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}