package com.example.demo.book;

import com.example.demo.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = repository.save(Book.builder()
                .title("Goblet of Fire")
                .author("J.K.Rowling")
                .genre("Fiction")
                .quantity(20)
                .price(32.5f)
                .build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll() {
         Book book = Book.builder()
                .title("To kill a mockingbird")
                .author("Harper Lee")
                .genre("Southern Gothic")
                .price(35.5F)
                .quantity(100)
                .build();
        repository.save(book);
        List<Book> all = repository.findAll();
        List<Book> result = repository.findBookByGenreOrTitleOrAuthor("Harper Lee");


        assertEquals(all.size(), result.size());
    }
}