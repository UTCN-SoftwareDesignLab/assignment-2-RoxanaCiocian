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
    public void testFindByFields() {
         Book book = Book.builder()
                .title("Morning Glory")
                .author("Stii tu cine")
                .genre("Funny fun fun")
                .quantity(100)
                .price(35.5F)
                .build();

        repository.save(book);

        List<Book> booksFound = repository.findBookByGenreOrTitleOrAuthor("Stii tu cine");


        assertEquals(repository.findAll().size(), booksFound.size());
    }
}