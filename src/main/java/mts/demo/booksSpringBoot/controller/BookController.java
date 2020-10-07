package mts.demo.booksSpringBoot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mts.demo.booksSpringBoot.exception.ResourceNotFoundException;
import mts.demo.booksSpringBoot.model.Book;
import mts.demo.booksSpringBoot.repository.BookRepository;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    // Get All Books
    @GetMapping("/books")
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    // Create a new Book
    @PostMapping("/books")
    public Book createBook(@Valid @RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Get a Single Book
    @GetMapping("/books_id/{id}")
    public Book getBookById(@PathVariable(value = "id") Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
    }

    // Update a Book
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable(value = "id") Long bookId,
                                            @Valid @RequestBody Book bookInfo) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        book.setBookName(bookInfo.getBookName());
        book.setBookDescription(bookInfo.getBookDescription());
        book.setPrice(bookInfo.getPrice());
        book.setCreatedDate(bookInfo.getCreatedDate());
        book.setPublishedDate(bookInfo.getPublishedDate());

        Book updatedBook = bookRepository.save(book);
        return updatedBook;
    }

    // Delete a Book
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        bookRepository.delete(book);

        return ResponseEntity.ok().build();
    }
}
