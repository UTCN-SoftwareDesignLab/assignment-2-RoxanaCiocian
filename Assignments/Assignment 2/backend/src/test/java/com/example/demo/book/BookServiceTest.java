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

import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

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
        Book book1 = TestCreationFactory.newBook();

        when(bookMapper.fromDto(book)).thenReturn(book1);
        when(bookMapper.toDto(book1)).thenReturn(book);
        when(bookRepository.save(book1)).thenReturn(book1);

        Assertions.assertEquals(book.getId(), bookService.create(book).getId());

    }

    @Test
    void update() {
        Long id = 15L;
        Book book = TestCreationFactory.newBook();
        book.setId(id);
        BookDTO bookDto = TestCreationFactory.newBookDTO();
        bookDto.setId(id);
//        BookDTO bookDto = BookDTO.builder()
//                .title(book.getTitle())
//                .quantity(book.getQuantity())
//                .author(book.getAuthor())
//                .genre(book.getGenre())
//                .price(book.getPrice())
//                .id(book.getId())
//                .build();
        System.out.println(book.toString());
        when(bookMapper.fromDto(bookDto)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        when(bookRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(book));
        when(bookRepository.save(book)).thenReturn(book);
        BookDTO editedBook = bookService.update(id, bookDto);
        Assertions.assertEquals(bookDto, editedBook);

        System.out.println(bookDto.toString());
        System.out.println(editedBook.toString());
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.deleteAll();

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(0, all.size());
    }

    @Test
    void search() {
    }

    @Test
    void sell() {
    }

    @Test
    void booksOutOfStock() {
    }
}