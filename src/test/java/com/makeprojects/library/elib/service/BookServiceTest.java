package com.makeprojects.library.elib.service;

import com.makeprojects.library.elib.ELibraryApplication;
import com.makeprojects.library.elib.entity.Book;
import com.makeprojects.library.elib.enums.Category;
import com.makeprojects.library.elib.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@SpringBootTest(classes = ELibraryApplication.class)
public class BookServiceTest {

    private final BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    static Book book = Book.builder()
            .id(UUID.randomUUID())
            .author("TestAuthor")
            .category(Category.FICTION)
            .description("TestDescription")
            .isbn("TestISBN")
            .name("TestBook")
            .price(300D).build();

    @Autowired
    public BookServiceTest(BookService bookService) {
        this.bookService = bookService;
    }


    @Test
    void getAllBooks_whenThereAre3Books() {
        List<Book> bookList = Arrays.asList(book, book.withId(UUID.randomUUID()).withPrice(400D), book.withId(UUID.randomUUID()).withPrice(250D));

        Mockito.when(this.bookRepository.findAll()).thenReturn(bookList);

        List<Book> fetchedBookList =  this.bookService.getAllBooks();

        Assertions.assertEquals(bookList.size(), fetchedBookList.size());
    }

    @Test
    void getAllBooks_whenThereAreNoBooks() {
        List<Book> bookList = Collections.emptyList();

        Mockito.when(this.bookRepository.findAll()).thenReturn(bookList);

        List<Book> fetchedBookList =  this.bookService.getAllBooks();

        Assertions.assertEquals(bookList.size(), fetchedBookList.size());
    }

    @Test
    void getBookById_whenBookIdExists() {
        Mockito.when(this.bookRepository.findById(book.getId()))
                .thenReturn(Optional.of(book));

        Book fetchedBook = this.bookService.getBookById(book.getId());

        Assertions.assertEquals(book, fetchedBook);
    }

    @Test
    void getBookById_whenBookIdDoestNotExist() {
        Book book1;
        UUID invalidID = UUID.randomUUID();
        Mockito.when(this.bookRepository.findById(invalidID))
                .thenReturn(Optional.empty());

        Book fetchedBook = this.bookService.getBookById(invalidID);

        Assertions.assertNull(fetchedBook);
    }

    @Test
    void addBook_whenBookIsAdded() {
        Mockito.when(this.bookRepository.save(book)).thenReturn(book);

        Book addedBook = this.bookService.addBook(book);

        Assertions.assertEquals(book, addedBook);
    }

    // TODO: ReVisit this code.
//    @Test
//    void addBook_whenBookIsNotAdded() {
//        Exception e = new Exception("Exception: {}");
//        Mockito.when(this.bookRepository.save(book)).thenThrow(e);
//
//        //Book addedBook = this.bookService.addBook(book);
//
//        Assertions.assertThrows(Exception.class, () -> {
//           this.bookService.addBook(book);
//        });
//    }
}
