package com.example.demo.book;


import com.example.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKSTORE)
@RequiredArgsConstructor
public class EmployeeBookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allBooks(){
        return bookService.findAll();
    }

    @PatchMapping(SELL + ENTITY + QUANTITY)
    public BookDTO sell(@PathVariable Long id, @PathVariable Integer quantity) {
        return bookService.sell(id, quantity);
    }

    @GetMapping(SEARCH)
    public List<BookDTO> search(@PathVariable String book) {
        return bookService.search(book);
    }


}
