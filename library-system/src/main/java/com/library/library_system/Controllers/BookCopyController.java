package com.library.library_system.Controllers;

import com.library.library_system.DTOS.BookCopyDTO;
import com.library.library_system.model.BookCopy;
import com.library.library_system.model.BookCopyStatus;
import com.library.library_system.services.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-copies")
public class BookCopyController {

    @Autowired
    private BookCopyService bookCopyService;

    // Create a new copy for a book
    @PostMapping
    public BookCopyDTO createCopy(@RequestParam Long bookId, @RequestParam String barcode) {
        return bookCopyService.createCopy(bookId, barcode);
    }

    // Get all copies of a book
    @GetMapping("/book/{bookId}/copies")
    public List<BookCopyDTO> getCopiesByBook(@PathVariable Long bookId) {
        return bookCopyService.getCopiesByBook(bookId);
    }


    // Update a copy of a book
    @PutMapping("/{copyId}")
    public BookCopyDTO updateBookCopy(@PathVariable Long copyId,
                                      @RequestBody BookCopyDTO copyDTO) {
        return bookCopyService.updateBookCopy(copyId, copyDTO);
    }

    // Delete a copy of a book
    @DeleteMapping("/{copyId}")
    public void deleteBookCopy(@PathVariable Long copyId) {
        bookCopyService.deleteBookCopy(copyId);
    }

}
