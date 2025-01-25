package com.makeprojects.library.elib.core.service.implementation;

import com.makeprojects.library.elib.core.service.definition.BookService;
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
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book get(UUID id) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);

        return bookOptional.orElse(null);
    }

    @Override
    public List<Book> getAll() {
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

    @Override
    public Book create(Book entity) {
        try {
            log.info("Saving a new book");
            Book savedBook = this.bookRepository.save(entity);
            log.info("Save a new book with id: {}", savedBook.getId());
            return savedBook;
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }

    @Override
    public Book update(Book entity) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
