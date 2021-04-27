package com.example.demo.book;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }


    @Test
    void create() {
        BookDTO book = TestCreationFactory.newBookDTO();

        when(bookService.create(book)).thenReturn(book);

        Assertions.assertEquals(book, bookService.create(book));

    }

    @Test
    void update() {
//        Long id = 15L;
//        BookDTO book1 = TestCreationFactory.newBookDTO();
//        book1.setId(id);
//
//        BookDTO bookDto =TestCreationFactory.newBookDTO();
//        when(bookService.create(book1)).thenReturn(book1);
//
//        bookDto.setId(id);
//        //when(bookService.findById(id)).thenReturn(java.util.Optional.ofNullable(book1));
//
//        BookDTO editedBook = bookService.update(id, bookDto);
//        Assertions.assertEquals(bookDto, editedBook);
//
//        System.out.println(bookDto.toString());
//        System.out.println(editedBook.toString());
    }

    @Test
    void deleteById() {
        BookDTO book = TestCreationFactory.newBookDTO();
        when(bookService.create(book)).thenReturn(book);
        bookService.deleteById(book.getId());
        assertEquals(bookService.findAll().size(),0);
    }

    @Test
    void deleteAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.deleteAll();

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(0, all.size());
    }


}