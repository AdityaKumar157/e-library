package com.makeprojects.library.elib.service;

import com.makeprojects.library.elib.entity.Book;
import com.makeprojects.library.elib.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        try {
            log.info("Saving a new book");
            Book savedBook = this.bookRepository.save(book);
            log.info("Save a new book with id: {}", savedBook.getId());
            return savedBook;
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }

    public List<Book> getAllBooks() {
        try {
            log.info("Getting all books");
            List<Book> books = this.bookRepository.findAll();
            log.info("Fetched total {} books.", books.size());
            return books;
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }

    public Book getBookById(UUID bookId) {
        Optional<Book> bookOptional = this.bookRepository.findById(bookId);

        return bookOptional.orElse(null);
    }
}
