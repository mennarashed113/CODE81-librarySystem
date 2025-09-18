package com.library.library_system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false, referencedColumnName = "id")
    private Book book;

    @Column(unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    private BookCopyStatus status = BookCopyStatus.AVAILABLE;

    private String location;
}
