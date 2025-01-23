package com.makeprojects.library.elib.repository;

import com.makeprojects.library.elib.ELibraryApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ELibraryApplication.class)
public class BookRepositoryTest {

    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
