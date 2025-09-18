package com.library.library_system.DTOS;

import com.library.library_system.model.Author;
import com.library.library_system.model.Book;
import com.library.library_system.model.Category;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public record BookDTO(
        Long id,
        String title,
        String isbn,
        String language,
        int publicationYear,
        String edition,
        String summary,
        String coverImageUrl,
        String publisher,
        Set<String> authors,
        Set<String> categories
) {
    public static BookDTO from(Book book) {
        Set<String> authorNames = book.getAuthors() != null
                ? book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet())
                : Set.of();

        Set<String> categoryNames = book.getCategories() != null
                ? book.getCategories().stream().map(Category::getName).collect(Collectors.toSet())
                : Set.of();

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getLanguage(),
                book.getPublicationYear(),
                book.getEdition(),
                book.getSummary(),
                book.getCoverImageUrl(),
                book.getPublisher() != null ? book.getPublisher().getName() : null,
                authorNames,
                categoryNames
        );
    }
}

