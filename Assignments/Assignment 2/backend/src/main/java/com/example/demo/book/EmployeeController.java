package com.example.demo.book;


import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKSTORE)
@RequiredArgsConstructor
public class EmployeeController {
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allBooks(){
        return bookService.findAll();
    }

    @PatchMapping(ENTITY + AMOUNT)
    public ResponseEntity<?> sell(@PathVariable Long id, @PathVariable int quantity) {
        if (bookService.sell(id, quantity)) {
            return ResponseEntity.ok(new MessageResponse("Successfully sold book"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Book not in stock"));
        }
    }
}
