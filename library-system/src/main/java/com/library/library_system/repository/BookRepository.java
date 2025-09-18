package com.library.library_system.repository;

import com.library.library_system.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    @EntityGraph(attributePaths = {"authors", "categories", "publisher"})
    List<Book> findAll();

    @EntityGraph(attributePaths = {"authors", "categories", "publisher"})
    Optional<Book> findById(Long id);
}
