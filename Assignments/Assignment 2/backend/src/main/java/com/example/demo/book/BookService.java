package com.example.demo.book;

import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO update(Long id, BookDTO book) {
        Book actBook = findById(id);

        if(!book.getTitle().equals("")) {
            actBook.setTitle(book.getTitle());
        }
        if(!book.getAuthor().equals("")) {
            actBook.setAuthor(book.getAuthor());
        }
        if(!book.getGenre().equals("")) {
            actBook.setGenre(book.getGenre());
        }
        if(book.getPrice() != null) {
            actBook.setPrice(book.getPrice());
        }
        if(book.getQuantity() != null) {
            actBook.setQuantity(book.getQuantity());
        }
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }


    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public void deleteAll(){
        bookRepository.deleteAll();

    }

    public List<BookDTO> search(String book){
        return bookRepository.findBookByGenreOrTitleOrAuthor(book).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO sell (Long id, Integer quantity)
    {
        Book actBook = findById(id);
        if(actBook.getQuantity() - quantity >= 0) {
            actBook.setQuantity(actBook.getQuantity() - quantity);
            bookRepository.save(actBook);
        }
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public List<BookDTO> booksOutOfStock(){
        List<BookDTO> booksOutOfStock = bookRepository.findAll().stream()
                .filter(book -> book.getQuantity() == 0)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());

        return booksOutOfStock;
    }
}
