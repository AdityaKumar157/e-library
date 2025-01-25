package com.makeprojects.library.elib.controller;

import com.makeprojects.library.elib.core.service.definition.BookService;
import com.makeprojects.library.elib.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book savedBook = this.bookService.create(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> bookList = this.bookService.getAll();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID bookId) {
        Book book = this.bookService.get(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // www.xyz.com/book/{bookId}    -- path parameter   @PathVariable
    // www.xyz.com/book?bookId=1234     -- Query/Request parameter      @RequestParam
}
