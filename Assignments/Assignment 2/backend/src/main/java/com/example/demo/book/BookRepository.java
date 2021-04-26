package com.example.demo.book;

import com.example.demo.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT book from Book book where book.genre LIKE ?1 OR book.title LIKE ?1 OR book.author LIKE ?1")
    List<Book> findBookByGenreOrTitleOrAuthor(String book);
}
